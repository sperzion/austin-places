package com.bunchcode.austinplaces.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.bunchcode.austinplaces.data.Venue
import com.bunchcode.austinplaces.data.persistence.AppDatabase
import com.bunchcode.austinplaces.network.SearchRepository
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel() {

    private val query: MutableLiveData<String> = MutableLiveData()

    val suggestions: LiveData<List<String>> = MutableLiveData()

    val searchResults: LiveData<List<Venue>> = MutableLiveData()

    val resultsAsMap: MutableLiveData<Boolean> by lazy {
        val initialized = MutableLiveData<Boolean>()
        initialized.value = false
        initialized
    }

    val canSearch: LiveData<Boolean> by lazy {
        val initialized = MutableLiveData<Boolean>()
        initialized.value = false
        initialized
    }

    private lateinit var favoritedIds: LiveData<List<String>>

    fun getFavoritedIds(context: Context): LiveData<List<String>> {

        if (!this::favoritedIds.isInitialized) {
            favoritedIds = AppDatabase.getInstance(context).getVenueStorage().getFavorited()
        }
        return favoritedIds
    }

    @SuppressLint("CheckResult")
    fun onQueryChanged(newQuery: String) {
        query.value = newQuery

        (canSearch as MutableLiveData).value = 3 <= newQuery.length
        if (!canSearch.value!!) {
            return
        }

        SearchRepository.getSuggestions(newQuery)
                .subscribeOn(Schedulers.io())
                .subscribe((suggestions as MutableLiveData)::postValue)
    }

    @SuppressLint("CheckResult")
    fun onSearchSubmitted(context: Context) {

        val storage = AppDatabase.getInstance(context).getVenueStorage()

        SearchRepository.searchVenues(query.value!!)
                .toFlowable()
                .flatMapIterable { it }
                .doOnNext { it.isFavorite = storage.isFavoritedSync(it.id) }
                .toList()
                .subscribeOn(Schedulers.io())
                .subscribe((searchResults as MutableLiveData)::postValue)
    }
}
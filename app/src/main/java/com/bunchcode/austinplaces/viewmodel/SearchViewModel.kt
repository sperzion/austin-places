package com.bunchcode.austinplaces.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bunchcode.austinplaces.data.Venue
import com.bunchcode.austinplaces.network.SearchRepositoryProvider
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

    @SuppressLint("CheckResult")
    fun onQueryChanged(newQuery: String) {
        query.value = newQuery

        (canSearch as MutableLiveData).value = 3 <= newQuery.length
        if (!canSearch.value!!) {
            return
        }

        SearchRepositoryProvider.get().getSuggestions(newQuery)
                .subscribeOn(Schedulers.io())
                .subscribe((suggestions as MutableLiveData)::postValue)
    }

    @SuppressLint("CheckResult")
    fun onSearchSubmitted() {
        SearchRepositoryProvider.get().searchVenues(query.value!!)
                .subscribeOn(Schedulers.io())
                .subscribe((searchResults as MutableLiveData)::postValue)
    }
}
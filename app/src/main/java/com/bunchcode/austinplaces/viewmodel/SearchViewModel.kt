package com.bunchcode.austinplaces.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bunchcode.austinplaces.network.SearchRepositoryProvider
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel() {

    private val query: MutableLiveData<String> = MutableLiveData()

    val suggestions: MutableLiveData<List<String>> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun onQueryChanged(newQuery: String) {
        query.value = newQuery

        SearchRepositoryProvider.get().getSuggestions(newQuery)
                .subscribeOn(Schedulers.io())
                .subscribe(suggestions::postValue)
    }
}
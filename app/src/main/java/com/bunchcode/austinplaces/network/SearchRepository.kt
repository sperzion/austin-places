package com.bunchcode.austinplaces.network

import io.reactivex.Single

class SearchRepository(private val apiService: FoursquareApiService) {

    fun getSuggestions(queryStart: String): Single<List<String>> {

        return apiService.getSuggestions(queryStart)
                .map { it.response }
                .toFlowable()
                .flatMapIterable { it.venues }
                .map { it.name }
                .toList()
    }
}

object SearchRepositoryProvider {

    fun get(): SearchRepository {
        return SearchRepository(FoursquareApiService.create())
    }
}
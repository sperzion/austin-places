package com.bunchcode.austinplaces.network

import com.bunchcode.austinplaces.data.Venue
import io.reactivex.Single

class SearchRepository {

    companion object {

        private val apiService: FoursquareApiService by lazy {
            FoursquareApiService.get()
        }

        fun getSuggestions(queryStart: String): Single<List<String>> {

            return apiService.getSuggestions(queryStart)
                    .map { it.response }
                    .toFlowable()
                    .flatMapIterable { it.venues }
                    .map { it.name }
                    .toList()
        }

        fun searchVenues(query: String): Single<List<Venue>> {

            return apiService.searchVenues(query)
                    .map { it.response.venues }
        }
    }
}

package com.bunchcode.austinplaces.network

import com.bunchcode.austinplaces.data.Venue
import io.reactivex.Single

class VenueRepository {

    companion object {

        private val apiService: FoursquareApiService by lazy {
            FoursquareApiService.get()
        }

        fun retrieveVenueDetails(venueId: String): Single<Venue> {

            return apiService.retrieveVenueDetails(venueId)
                    .map { it.response.venue }
        }
    }
}

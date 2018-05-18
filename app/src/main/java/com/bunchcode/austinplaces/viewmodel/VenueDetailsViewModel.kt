package com.bunchcode.austinplaces.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.bunchcode.austinplaces.data.Venue
import com.bunchcode.austinplaces.data.persistence.AppDatabase
import com.bunchcode.austinplaces.network.VenueRepository
import io.reactivex.schedulers.Schedulers

class VenueDetailsViewModel: ViewModel() {

    val venueLiveData: LiveData<Venue> by lazy {
        MutableLiveData<Venue>()
    }

    @SuppressLint("CheckResult")
    fun retrieveVenueDetails(venueId: String, context: Context) {

        val storage = AppDatabase.getInstance(context).getVenueStorage()

        VenueRepository.retrieveVenueDetails(venueId)
                .doOnSuccess { it.isFavorite = storage.isFavoritedSync(venueId) }
                .subscribeOn(Schedulers.io())
                .subscribe((venueLiveData as MutableLiveData)::postValue)
    }

    fun onFavoriteActionToggled(venue: Venue, context: Context) {
        val storage = AppDatabase.getInstance(context).getVenueStorage()
        if (venue.isFavorite) storage.saveVenue(venue)
        else storage.deleteVenue(venue)
    }

    fun isFavorited(venueId: String, context: Context): LiveData<Boolean> {
        return AppDatabase.getInstance(context).getVenueStorage().isFavorited(venueId)
    }
}

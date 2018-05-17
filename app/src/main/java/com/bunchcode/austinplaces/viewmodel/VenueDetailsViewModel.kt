package com.bunchcode.austinplaces.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bunchcode.austinplaces.data.Venue
import com.bunchcode.austinplaces.network.VenueRepository
import io.reactivex.schedulers.Schedulers

class VenueDetailsViewModel: ViewModel() {

    val venueLiveData: LiveData<Venue> by lazy {
        MutableLiveData<Venue>()
    }

    @SuppressLint("CheckResult")
    fun retrieveVenueDetails(venueId: String) {

        VenueRepository.retrieveVenueDetails(venueId)
                .subscribeOn(Schedulers.io())
                .subscribe((venueLiveData as MutableLiveData)::postValue)
    }
}

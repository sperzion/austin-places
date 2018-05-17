package com.bunchcode.austinplaces.app

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.bunchcode.austinplaces.R
import com.bunchcode.austinplaces.app.AppUtils.Companion.AUSTIN_LATITUDE
import com.bunchcode.austinplaces.app.AppUtils.Companion.AUSTIN_LONGITUDE
import com.bunchcode.austinplaces.app.AppUtils.Companion.addMarker
import com.bunchcode.austinplaces.app.AppUtils.Companion.hue
import com.bunchcode.austinplaces.data.Venue
import com.bunchcode.austinplaces.viewmodel.SearchViewModel
import com.google.android.gms.maps.CameraUpdateFactory.newLatLngBounds
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

class SearchMapFragment: SupportMapFragment() {

    lateinit var searchViewModel: SearchViewModel
    lateinit var map: GoogleMap

    companion object {

        fun newInstance(): SearchMapFragment = SearchMapFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchViewModel = ViewModelProviders.of(activity!!).get(SearchViewModel::class.java)

        getMapAsync {
            map = it
            map.uiSettings.isMapToolbarEnabled = false
            map.setOnInfoWindowClickListener {
                it.tag?.let {
                    VenueDetailsActivity.start(it as Venue, activity!!)
                }
            }

            updateMap(null)
            attachViewModelObservers()
        }
    }

    private fun attachViewModelObservers() {
        searchViewModel.searchResults.observe(this, Observer { updateMap(it) })
    }

    private fun updateMap(venues: List<Venue>?) {
        // Clear all markers
        map.clear()

        // Add marker for Austin center
        addMarker(map,
                getString(R.string.label_austin),
                AUSTIN_LATITUDE,
                AUSTIN_LONGITUDE,
                hue(context!!.getColor(R.color.colorAccent)))

        // Add markers for each venue
        val markerHue = hue(context!!.getColor(R.color.colorPrimary))
        var minLat: Double = AUSTIN_LATITUDE
        var minLng: Double = AUSTIN_LONGITUDE
        var maxLat: Double = AUSTIN_LATITUDE
        var maxLng: Double = AUSTIN_LONGITUDE

        venues?.forEach {
            addMarker(map, it, markerHue)
            minLat = Math.min(minLat, it.location.latitude)
            minLng = Math.min(minLng, it.location.longitude)
            maxLat = Math.max(maxLat, it.location.latitude)
            maxLng = Math.max(maxLng, it.location.longitude)
        }

        // Update camera position and zoom
        map.moveCamera(newLatLngBounds(
                LatLngBounds(LatLng(minLat, minLng), LatLng(maxLat, maxLng)),
                resources.getDimensionPixelSize(R.dimen.map_padding)))
    }
}

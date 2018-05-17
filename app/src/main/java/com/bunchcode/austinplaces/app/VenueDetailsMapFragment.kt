package com.bunchcode.austinplaces.app

import android.os.Bundle
import com.bunchcode.austinplaces.R
import com.bunchcode.austinplaces.app.AppUtils.Companion.AUSTIN_LATITUDE
import com.bunchcode.austinplaces.app.AppUtils.Companion.AUSTIN_LONGITUDE
import com.bunchcode.austinplaces.app.AppUtils.Companion.addMarker
import com.bunchcode.austinplaces.app.AppUtils.Companion.hue
import com.bunchcode.austinplaces.data.Venue
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import kotlin.math.max
import kotlin.math.min

class VenueDetailsMapFragment: SupportMapFragment() {

    lateinit var map: GoogleMap
    lateinit var venue: Venue

    companion object {

        private const val ARG_VENUE: String = "VenueDetailsMapFragment.arg.venue"

        fun newInstance(venue: Venue): VenueDetailsMapFragment {
            val args = Bundle()
            args.putParcelable(ARG_VENUE, venue)

            val instance = VenueDetailsMapFragment()
            instance.arguments = args
            return instance
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        venue = arguments!!.getParcelable(ARG_VENUE)

        getMapAsync {
            map = it
            map.uiSettings.setAllGesturesEnabled(false)
            map.uiSettings.isMapToolbarEnabled = false

            map.setOnMapLoadedCallback {
                updateMap()
            }
        }
    }

    private fun updateMap() {
        // Clear markers
        map.clear()

        // Add marker for Austin center
        addMarker(map,
                getString(R.string.label_austin),
                AUSTIN_LATITUDE,
                AUSTIN_LONGITUDE,
                hue(context!!.getColor(R.color.colorAccent)))

        // Add marker for venue
        addMarker(map,
                venue.name,
                venue.location.latitude,
                venue.location.longitude,
                hue(context!!.getColor(R.color.colorPrimary)))

        // Update camera position and zoom
        val minLat = min(AUSTIN_LATITUDE, venue.location.latitude)
        val minLng = min(AUSTIN_LONGITUDE, venue.location.longitude)
        val maxLat = max(AUSTIN_LATITUDE, venue.location.latitude)
        val maxLng = max(AUSTIN_LONGITUDE, venue.location.longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(
                LatLngBounds(LatLng(minLat, minLng), LatLng(maxLat, maxLng)),
                resources.getDimensionPixelSize(R.dimen.map_padding)))
    }
}
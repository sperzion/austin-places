package com.bunchcode.austinplaces.app

import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.bunchcode.austinplaces.data.Venue
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Math.*

sealed class AppUtils {

    companion object {

        const val AUSTIN_LATITUDE = 30.2672
        const val AUSTIN_LONGITUDE = -97.7431

        fun milesFromAustin(latitude: Double, longitude: Double): Double {

            val deltaLat = toRadians(AUSTIN_LATITUDE - latitude)
            val deltaLong = toRadians(AUSTIN_LONGITUDE - longitude)

            val a = pow(sin(deltaLat / 2), 2.0) +
                    cos(toRadians(latitude)) * cos(toRadians(AUSTIN_LATITUDE)) *
                    pow(sin(deltaLong / 2), 2.0)
            val c = 2 * atan2(sqrt(a), sqrt(1 - a))

            return c * 3959
        }

        fun hue(color: Int): Float {

            val hsv = FloatArray(3, { 0f })
            Color.colorToHSV(color, hsv)
            return hsv[0]
        }

        fun hideKeyboard(view: View) {
            val inputManager = view.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun addMarker(map: GoogleMap,
                name: String,
                latitude: Double,
                longitude: Double,
                hue: Float): Marker {

            return map.addMarker(MarkerOptions()
                    .title(name)
                    .position(LatLng(latitude, longitude))
                    .icon(BitmapDescriptorFactory.defaultMarker(hue)))
        }

        fun addMarker(map: GoogleMap, venue: Venue, hue: Float): Marker {

            val marker = addMarker(map,
                    venue.name,
                    venue.location.latitude,
                    venue.location.longitude,
                    hue)
            marker.tag = venue
            return marker
        }
    }
}
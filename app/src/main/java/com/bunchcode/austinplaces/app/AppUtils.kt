package com.bunchcode.austinplaces.app

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
    }
}
package com.bunchcode.austinplaces.data

import com.google.gson.annotations.SerializedName

data class Location(@SerializedName("lat") val latitude: Double,
                    @SerializedName("lng") val longitude: Double,
                    @SerializedName("formattedAddress") val formattedAddress: Array<String>?) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Location

        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false

        return true
    }

    override fun hashCode(): Int {
        var result = latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        return result
    }
}
package com.bunchcode.austinplaces.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class Location(@SerializedName("formattedAddress") val formattedAddress: Array<String>?) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Location
        return Arrays.equals(formattedAddress, other.formattedAddress)
    }

    override fun hashCode(): Int {
        return formattedAddress?.let { Arrays.hashCode(it) } ?: 0
    }
}
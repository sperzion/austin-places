package com.bunchcode.austinplaces.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PriceInfo(@SerializedName("message") val tier: String?) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tier)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PriceInfo> {
        override fun createFromParcel(parcel: Parcel): PriceInfo {
            return PriceInfo(parcel)
        }

        override fun newArray(size: Int): Array<PriceInfo?> {
            return arrayOfNulls(size)
        }
    }

}

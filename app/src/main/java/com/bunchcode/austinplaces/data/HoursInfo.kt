package com.bunchcode.austinplaces.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class HoursInfo(@SerializedName("status") val status: String?) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HoursInfo> {
        override fun createFromParcel(parcel: Parcel): HoursInfo {
            return HoursInfo(parcel)
        }

        override fun newArray(size: Int): Array<HoursInfo?> {
            return arrayOfNulls(size)
        }
    }

}

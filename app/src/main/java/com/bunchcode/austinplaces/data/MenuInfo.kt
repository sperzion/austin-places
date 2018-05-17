package com.bunchcode.austinplaces.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MenuInfo(@SerializedName("mobileUrl") val url: String?) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MenuInfo> {
        override fun createFromParcel(parcel: Parcel): MenuInfo {
            return MenuInfo(parcel)
        }

        override fun newArray(size: Int): Array<MenuInfo?> {
            return arrayOfNulls(size)
        }
    }

}

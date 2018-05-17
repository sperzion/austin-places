package com.bunchcode.austinplaces.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Category(@SerializedName("name") val name: String,
                    @SerializedName("shortName") val shortName: String,
                    @SerializedName("icon") val icon: CategoryIcon) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(CategoryIcon::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(shortName)
        parcel.writeParcelable(icon, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}

data class CategoryIcon(@SerializedName("prefix") private val prefix: String,
                        @SerializedName("suffix") private val suffix: String) : Parcelable {

    val url: String get() = prefix + "bg_64" + suffix

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(prefix)
        parcel.writeString(suffix)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CategoryIcon> {
        override fun createFromParcel(parcel: Parcel): CategoryIcon {
            return CategoryIcon(parcel)
        }

        override fun newArray(size: Int): Array<CategoryIcon?> {
            return arrayOfNulls(size)
        }
    }
}
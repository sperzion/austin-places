package com.bunchcode.austinplaces.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Venue(@SerializedName("id") val id: String,
                 @SerializedName("name") val name: String,
                 @SerializedName("location") val location: Location,
                 @SerializedName("categories") val categories: List<Category>,
                 @SerializedName("url") val url: String?,
                 @SerializedName("contact") private val contact: ContactInfo?,
                 @SerializedName("price") private val price: PriceInfo?,
                 @SerializedName("hours") private val hours: HoursInfo?,
                 @SerializedName("menu") private val menu: MenuInfo?
) : Parcelable {

    val formattedPhone: String? get() = contact?.phone
    val priceTier: String? get() = price?.tier
    val hoursStatus: String? get() = hours?.status
    val menuUrl: String? get() = menu?.url

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Location::class.java.classLoader),
            parcel.createTypedArrayList(Category),
            parcel.readString(),
            parcel.readParcelable(ContactInfo::class.java.classLoader),
            parcel.readParcelable(PriceInfo::class.java.classLoader),
            parcel.readParcelable(HoursInfo::class.java.classLoader),
            parcel.readParcelable(MenuInfo::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeParcelable(location, flags)
        parcel.writeTypedList(categories)
        parcel.writeString(url)
        parcel.writeParcelable(contact, flags)
        parcel.writeParcelable(price, flags)
        parcel.writeParcelable(hours, flags)
        parcel.writeParcelable(menu, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Venue> {
        override fun createFromParcel(parcel: Parcel): Venue {
            return Venue(parcel)
        }

        override fun newArray(size: Int): Array<Venue?> {
            return arrayOfNulls(size)
        }
    }

}


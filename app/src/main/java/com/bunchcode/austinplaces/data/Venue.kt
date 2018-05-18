package com.bunchcode.austinplaces.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Entity
data class Venue(
        @PrimaryKey
        @SerializedName("id")
        val id: String,

        @ColumnInfo(name = "name")
        @SerializedName("name")
        val name: String,

        @ColumnInfo(name = "isFavorite")
        var isFavorite: Boolean = false

) : Parcelable {

    @Ignore
    @SerializedName("location")
    lateinit var location: Location

    @Ignore
    @SerializedName("categories")
    lateinit var categories: List<Category>

    @Ignore
    @SerializedName("url")
    var url: String? = null

    @Ignore
    @SerializedName("contact")
    private var contact: ContactInfo? = null

    @Ignore
    @SerializedName("price")
    private var price: PriceInfo? = null

    @Ignore
    @SerializedName("hours")
    private var hours: HoursInfo? = null

    @Ignore
    @SerializedName("menu")
    private var menu: MenuInfo? = null


    val formattedPhone: String? get() = contact?.phone
    val priceTier: String? get() = price?.tier
    val hoursStatus: String? get() = hours?.status
    val menuUrl: String? get() = menu?.url

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte()) {
        location = parcel.readParcelable(Location::class.java.classLoader)
        categories = parcel.createTypedArrayList(Category)
        url = parcel.readString()
        contact = parcel.readParcelable(ContactInfo::class.java.classLoader)
        price = parcel.readParcelable(PriceInfo::class.java.classLoader)
        hours = parcel.readParcelable(HoursInfo::class.java.classLoader)
        menu = parcel.readParcelable(MenuInfo::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeByte(if (isFavorite) 1 else 0)
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


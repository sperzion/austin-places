package com.bunchcode.austinplaces.data

import com.google.gson.annotations.SerializedName

data class Venue(@SerializedName("id") val id: String,
                 @SerializedName("name") val name: String,
                 @SerializedName("location") val location: Location,
                 @SerializedName("categories") val categories: List<Category>)
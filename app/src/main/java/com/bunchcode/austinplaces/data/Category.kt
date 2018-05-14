package com.bunchcode.austinplaces.data

import com.google.gson.annotations.SerializedName

data class Category(@SerializedName("name") val name: String,
                    @SerializedName("shortName") val shortName: String)
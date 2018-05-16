package com.bunchcode.austinplaces.data

import com.google.gson.annotations.SerializedName

data class Category(@SerializedName("name") val name: String,
                    @SerializedName("shortName") val shortName: String,
                    @SerializedName("icon") val icon: CategoryIcon)

data class CategoryIcon(@SerializedName("prefix") private val prefix: String,
                        @SerializedName("suffix") private val suffix: String) {

    val url: String get() = prefix + "bg_64" + suffix
}
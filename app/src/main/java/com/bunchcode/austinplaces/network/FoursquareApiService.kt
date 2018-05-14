package com.bunchcode.austinplaces.network

import com.bunchcode.austinplaces.data.Venue
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FoursquareApiService {

    @GET("venues/suggestcompletion?near=$AUSTIN_TX")
    fun getSuggestions(@Query("query") queryStart: String): Single<SuggestionsResult>

    companion object {

        const val BASE_URL = "https://api.foursquare.com/v2/"
        const val VERSION = "20180512"
        const val CLIENT_ID = "KR5OSTO5FBKLXURDMZU3MXQMKO0WU243MYJIU0OGLTNRUI1Z"
        const val CLIENT_SECRET = "YMCXCWFEEZMT2TXLQGYKVQ0PAHNOPY0S5H252NF20AWIEHJJ"
        const val AUSTIN_TX = "Austin, TX"

        fun create(): FoursquareApiService {
            
            val httpClient = OkHttpClient.Builder()
                    .addInterceptor {
                        val request = it.request()
                        val modifiedUrl = request.url().newBuilder()
                                .addQueryParameter("client_id", CLIENT_ID)
                                .addQueryParameter("client_secret", CLIENT_SECRET)
                                .addQueryParameter("v", VERSION)
                                .build()
                        it.proceed(request.newBuilder().url(modifiedUrl).build())
                    }
                    .build()
            
            return Retrofit.Builder()
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
                    .create(FoursquareApiService::class.java)
        }
    }
}

data class SuggestionsResult(@SerializedName("response") val response: SuggestionsResponse)
data class SuggestionsResponse(@SerializedName("minivenues") val venues: List<Venue>)

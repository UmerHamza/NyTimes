package com.example.nyarticles.network.interfaces

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @GET("mostviewed/all-sections/{period}.json")
    fun getGeoFencesList(
        @Path("period") period: Int,
        @Query("api-key") key: String
    ): Call<JsonObject>
}


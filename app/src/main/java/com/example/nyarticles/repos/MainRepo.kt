package com.example.nyarticles.repos

import com.example.nyarticles.network.RetrofitCalling
import com.example.nyarticles.network.RetrofitController
import com.example.nyarticles.network.interfaces.apiRespoInterfaces.RepositoryListener
import com.example.nyarticles.network.interfaces.apiRespoInterfaces.RetrofitCallingListener
import com.google.gson.JsonObject
import retrofit2.Response

class MainRepo(private val repositoryListener: RepositoryListener) : RetrofitCallingListener {
    lateinit var key: String

    fun getNyArticles(period: Int, apikey: String) {
        key = "Articles"

        val apiService = RetrofitController.apiService.getGeoFencesList(period, apikey)
        val retrofitCalling = RetrofitCalling(this, key, apiService)
        retrofitCalling.apiCalling()
    }
    override fun onSuccessResponse(key: String, response: Response<JsonObject>?) {
        if (response!!.isSuccessful)
            repositoryListener.onSuccessResponse(key, response.body()!!.toString())
        else
            repositoryListener.onFailureResponse(key, "Error")
    }

    override fun onFailureResponse(key: String, error: String) {
        repositoryListener.onFailureResponse(key, "Error")
    }
}
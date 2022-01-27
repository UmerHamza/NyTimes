package com.example.nyarticles.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.nyarticles.R
import com.example.nyarticles.model.ResponseData
import com.example.nyarticles.network.interfaces.apiRespoInterfaces.RepositoryListener
import com.example.nyarticles.repos.MainRepo
import com.google.gson.Gson
import org.json.JSONObject

class MainViewModel(application: Application) : AndroidViewModel(application), RepositoryListener {

    private val TAG = "MainViewModel"
    private val repo = MainRepo(this)
    private val context = application

    val articlesList: MutableLiveData<ResponseData> by lazy {
        MutableLiveData<ResponseData>()
    }

    fun getArticles(i: Int) {
                repo.getNyArticles(i, context.getString(R.string.api_key))
    }

    override fun onSuccessResponse(key: String, result: String) {
        val jsonObject=JSONObject(result)

        if (jsonObject.optString("status") == "OK"){

            val jsonArray = jsonObject.optJSONArray("results")
            val ab = "{\"results\":$jsonArray}"
            val rsp = Gson().fromJson(ab, ResponseData::class.java)

            articlesList.value=  rsp

        }else{
            Toast.makeText(context,"Somethig went wrong",Toast.LENGTH_SHORT).show()

        }

    }

    override fun onFailureResponse(key: String, error: String) {
        Log.e(TAG, "onFailureResponse  : $key :  $error")
    }
}
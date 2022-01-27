package com.example.nyarticles.network

import android.util.Log
import com.example.nyarticles.network.interfaces.apiRespoInterfaces.RetrofitCallingListener
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitCalling(
    var retrofitCallingListener: RetrofitCallingListener,
    var key: String,
    var call: Call<JsonObject>
) {

    var job: CompletableJob = Job()

    //var homeActivity: HomeActivity? = null

    fun apiCalling() {
        /*CoroutineScope(Dispatchers.Main + job).launch {
            //homeActivity = HomeActivity()
        }*/

        CoroutineScope(Dispatchers.Default + job).launch {


            call.enqueue(object : Callback<JsonObject> {

                override fun onResponse(
                    call: Call<JsonObject>?,
                    response: Response<JsonObject>?
                ) {

                    job.complete()
                    val resp = response!!.body()

                    if (resp != null) {


                        retrofitCallingListener.onSuccessResponse(key, response)
                    }
                }

                override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                    Log.e("RetrofitCalling", t.toString())
                    job.complete()
                    retrofitCallingListener.onFailureResponse(key, t.toString())
                }
            })
        }
    }
}
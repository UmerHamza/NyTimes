package com.example.nyarticles.network

import com.example.nyarticles.BuildConfig
import com.example.nyarticles.network.interfaces.RetrofitService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitController {

    private val SERVER_ENDPOINT = "https://api.nytimes.com/svc/mostpopular/v2/"

    private val client: OkHttpClient by lazy {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        OkHttpClient.Builder()
            .readTimeout(80, TimeUnit.SECONDS)
            .writeTimeout(80, TimeUnit.SECONDS)
            .connectTimeout(80, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(RetrofitInterceptor(BuildConfig.VERSION_NAME))
//                .authenticator(RetrofitAuthenticator())
            .build()
    }

    private val retrofitController: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(SERVER_ENDPOINT)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: RetrofitService by lazy {
        retrofitController
            .build()
            .create(RetrofitService::class.java)
    }


}
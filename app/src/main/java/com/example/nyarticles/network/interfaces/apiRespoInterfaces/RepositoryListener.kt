package com.example.nyarticles.network.interfaces.apiRespoInterfaces

interface RepositoryListener {
    fun onSuccessResponse(key: String, result: String)
    fun onFailureResponse(key: String, error: String)
}
package com.example.nyarticles.model

data class ResponseData(
    val status: String,
    val num_results: Int,
    val results: ArrayList<Results>?=null
)
package com.example.nyarticles.model

import java.io.Serializable

data class Results(
    val url: String = "",
    val published_date: String,
    val title: String,
    val abstract: String,
    val media: ArrayList<Medias>? = null
) : Serializable

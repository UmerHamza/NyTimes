package com.example.nyarticles.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Medias(
    val caption: String,
    val copyright: String,
    @SerializedName("media-metadata")
    val media_metadata: ArrayList<Media_Metadata>? = null
) : Serializable
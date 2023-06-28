package com.gnovatto.challengemeli.domain.model

import com.google.gson.annotations.SerializedName

data class ProductDescriptionResponse (
    @SerializedName("plain_text")
    val plainText: String,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("date_created")
    val dateCreated: String,
)
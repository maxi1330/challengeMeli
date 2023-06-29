package com.gnovatto.challengemeli.domain.model

import com.google.gson.annotations.SerializedName

data class ProductSearchResponse(
    @SerializedName("query")
    val query: String,
    @SerializedName("results")
    val results: List<ProductModel>,
)
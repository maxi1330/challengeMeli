package com.gnovatto.challengemeli.domain.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ProductModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("condition")
    val condition : String,
    @SerializedName("category_id")
    val categoryId : String,
    @SerializedName("currency_id")
    val currencyId : String,
    @SerializedName("price")
    val price: String,
    @SerializedName("sold_quantity")
    val soldQuantity : Int,
    @SerializedName("available_quantity")
    val availableQuantity : Int,
) : Serializable
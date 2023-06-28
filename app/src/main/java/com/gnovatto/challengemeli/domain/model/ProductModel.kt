package com.gnovatto.challengemeli.domain.model
data class ProductModel(
    val id: String,
    val title: String,
    val productImage: String,
    val condition : String,
    val categoryId : String,
    val currencyId : String,
    val price: String,
    val soldQuantity : Int,
    val availableQuantity : Int,
)
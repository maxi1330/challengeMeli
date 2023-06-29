package com.gnovatto.challengemeli.domain.repository

import com.gnovatto.challengemeli.domain.model.ProductDescriptionResponse
import com.gnovatto.challengemeli.domain.model.ProductModel
import com.gnovatto.challengemeli.domain.model.ResultState
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getFilteredProducts(
        query: String,
        page: Int
    ): Flow<ResultState<List<ProductModel>, String>>

    suspend fun getProductDescription(productId: String): Flow<ResultState<ProductDescriptionResponse, String>>
}
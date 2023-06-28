package com.gnovatto.challengemeli.domain.repository

import com.gnovatto.challengemeli.common.ResultState
import com.gnovatto.challengemeli.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductsRepository @Inject constructor(
//    private val productService: null
) {
    suspend fun getFilteredProducts(query: String, page: Int): Flow<ResultState<List<ProductModel>, String>> = flow {
        emit(ResultState.Error("Hola"))
    }
}
package com.gnovatto.challengemeli.domain.repository

import com.gnovatto.challengemeli.common.extensions.handleExceptions
import com.gnovatto.challengemeli.data.dataSource.api.DescriptionProductApi
import com.gnovatto.challengemeli.domain.model.ResultState
import com.gnovatto.challengemeli.data.dataSource.api.ProductSearchApi
import com.gnovatto.challengemeli.domain.model.ProductDescriptionResponse
import com.gnovatto.challengemeli.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductsRepository @Inject constructor(
        private val provideSearchProductApi: ProductSearchApi,
        private val provideDescriptionProductApi: DescriptionProductApi
) {
    suspend fun getFilteredProducts(query: String, page: Int): Flow<ResultState<List<ProductModel>, String>> = flow {
        try {
            val listProducts = provideSearchProductApi.getProducts(query,page,20).results
            emit(ResultState.Success(listProducts))
        } catch (e: Exception) {
            emit(e.handleExceptions())
        }
    }

    suspend fun getProductDescription(productId: String): Flow<ResultState<ProductDescriptionResponse, String>> = flow {
        try {
            val description = provideDescriptionProductApi.getProductsDescription(productId)
            emit(ResultState.Success(description))
        } catch (e: Exception) {
            emit(e.handleExceptions())
        }
    }
}

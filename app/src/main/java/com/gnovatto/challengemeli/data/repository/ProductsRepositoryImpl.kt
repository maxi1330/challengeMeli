package com.gnovatto.challengemeli.data.repository

import com.gnovatto.challengemeli.common.extensions.handleExceptions
import com.gnovatto.challengemeli.data.source.DescriptionProductApi
import com.gnovatto.challengemeli.data.source.ProductSearchApi
import com.gnovatto.challengemeli.domain.model.ProductDescriptionResponse
import com.gnovatto.challengemeli.domain.model.ProductModel
import com.gnovatto.challengemeli.domain.model.ResultState
import com.gnovatto.challengemeli.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val provideSearchProductApi: ProductSearchApi,
    private val provideDescriptionProductApi: DescriptionProductApi
) : ProductsRepository {

    override suspend fun getFilteredProducts(
        query: String,
        page: Int
    ): Flow<ResultState<List<ProductModel>, String>> = flow {
        try {
            val listProducts = provideSearchProductApi.getProducts(query, page, 20).results
            emit(ResultState.Success(listProducts))
        } catch (e: Exception) {
            emit(e.handleExceptions())
        }
    }

    override suspend fun getProductDescription(productId: String): Flow<ResultState<ProductDescriptionResponse, String>> =
        flow {
            try {
                val description = provideDescriptionProductApi.getProductsDescription(productId)
                emit(ResultState.Success(description))
            } catch (e: Exception) {
                emit(e.handleExceptions())
            }
        }
}

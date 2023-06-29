package com.gnovatto.challengemeli.data.repository

import com.gnovatto.challengemeli.common.Constants.Companion.LIMIT_PAGE
import com.gnovatto.challengemeli.common.extensions.handleExceptionsNetwork
import com.gnovatto.challengemeli.data.source.DetailProductApi
import com.gnovatto.challengemeli.data.source.ProductSearchApi
import com.gnovatto.challengemeli.domain.model.ProductModel
import com.gnovatto.challengemeli.domain.model.ResultState
import com.gnovatto.challengemeli.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import javax.inject.Inject

/**
 * Implementación de la interfaz ProductsRepository.
 */
class ProductsRepositoryImpl @Inject constructor(
    private val provideSearchProductApi: ProductSearchApi,
    private val provideDetailProductApi: DetailProductApi
) : ProductsRepository {

    override suspend fun getFilteredProducts(
        query: String,
        page: Int
    ): Flow<ResultState<List<ProductModel>, String>> = flow {
        try {
            val listProducts = provideSearchProductApi.getProducts(query, page, LIMIT_PAGE).results
            emit(ResultState.Success(listProducts))
        } catch (e: Exception) {
            emit(e.handleExceptionsNetwork())
        }
    }

    override suspend fun getProductDetail(productId: String): Flow<ResultState<ProductModel, String>> =
        flow {
            try {
                val productDescriptionFlow = flowOf(provideDetailProductApi.getProductsDescription(productId))
                val productDetailFlow = flowOf(provideDetailProductApi.getDetailProduct(productId))

                val resultDetail = productDescriptionFlow.combine(productDetailFlow) { description, product ->
                    product.apply {
                        this.description = description.plainText
                    }
                }.single()

                emit(ResultState.Success(resultDetail))
            } catch (e: Exception) {
                emit(e.handleExceptionsNetwork())
            }
        }
}

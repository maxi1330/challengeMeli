package com.gnovatto.challengemeli.domain.repository

import com.bumptech.glide.load.HttpException
import com.gnovatto.challengemeli.common.Constants
import com.gnovatto.challengemeli.data.dataSource.api.DescriptionProductApi
import com.gnovatto.challengemeli.domain.model.ResultState
import com.gnovatto.challengemeli.data.dataSource.api.ProductSearchApi
import com.gnovatto.challengemeli.domain.model.ProductDescriptionResponse
import com.gnovatto.challengemeli.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ProductsRepository @Inject constructor(
        private val provideSearchProductApi: ProductSearchApi,
        private val provideDescriptionProductApi: DescriptionProductApi
) {
    suspend fun getFilteredProducts(query: String, page: Int): Flow<ResultState<List<ProductModel>, String>> = flow {
        try {
            val listProducts = provideSearchProductApi.getProducts(query,page,20).results
            emit(ResultState.Success(listProducts))
        } catch(e: IOException) {
            emit(ResultState.Error(Constants.ERROR_IO_EXCEPTION))
        } catch(e: HttpException) {
            emit(ResultState.Error(Constants.ERROR_HTTP_EXCEPTION))
        }
    }

    suspend fun getProductDetail(productId: String): Flow<ResultState<ProductDescriptionResponse, String>> = flow {
        try {
            val description = provideDescriptionProductApi.getProductsDescription(productId)
            emit(ResultState.Success(description))
        } catch(e: IOException) {
            emit(ResultState.Error(Constants.ERROR_IO_EXCEPTION))
        } catch(e: HttpException) {
            emit(ResultState.Error(Constants.ERROR_HTTP_EXCEPTION))
        }
    }
}

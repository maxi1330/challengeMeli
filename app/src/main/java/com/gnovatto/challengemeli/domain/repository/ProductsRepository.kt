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
        emit(ResultState.Success(listOf( ProductModel("1", "Batidora De Mano Con Bowl Mondial B05np 350w", "http://http2.mlstatic.com/D_613761-MLA43972286206_112020-I.jpg", "New", "category1", "USD", "10.99", 50, 100),
            ProductModel("2", "Batidora De Mano Con Bowl Mondial B05np 350w", "http://http2.mlstatic.com/D_613761-MLA43972286206_112020-I.jpg", "New", "category1", "USD", "10.99", 50, 100),
            ProductModel("3", "Batidora De Mano Con Bowl Mondial B05np 350w", "http://http2.mlstatic.com/D_613761-MLA43972286206_112020-I.jpg", "New", "category1", "USD", "10.99", 50, 100))))
    }
}

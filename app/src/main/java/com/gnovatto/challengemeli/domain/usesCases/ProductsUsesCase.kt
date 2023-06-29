package com.gnovatto.challengemeli.domain.usesCases

import com.gnovatto.challengemeli.data.repository.ProductsRepositoryImpl
import com.gnovatto.challengemeli.domain.model.ProductModel
import com.gnovatto.challengemeli.domain.model.ResultState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsUsesCase @Inject constructor(
    private val productsRepositoryImpl: ProductsRepositoryImpl
) {
    suspend operator fun invoke(
        query: String,
        page: Int
    ): Flow<ResultState<List<ProductModel>, String>> {
        return productsRepositoryImpl.getFilteredProducts(query, page)
    }

}
package com.gnovatto.challengemeli.domain.usesCases

import com.gnovatto.challengemeli.data.repository.ProductsRepositoryImpl
import com.gnovatto.challengemeli.domain.model.ProductModel
import com.gnovatto.challengemeli.domain.model.ResultState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailUsesCase @Inject constructor(
    private val productsRepositoryImpl: ProductsRepositoryImpl
) {
    suspend operator fun invoke(productId: String): Flow<ResultState<ProductModel, String>> {
        return productsRepositoryImpl.getProductDetail(productId)
    }
}
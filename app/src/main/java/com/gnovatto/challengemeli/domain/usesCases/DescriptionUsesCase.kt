package com.gnovatto.challengemeli.domain.usesCases

import com.gnovatto.challengemeli.data.repository.ProductsRepositoryImpl
import com.gnovatto.challengemeli.domain.model.ProductDescriptionResponse
import com.gnovatto.challengemeli.domain.model.ResultState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DescriptionUsesCase @Inject constructor(
    private val productsRepositoryImpl: ProductsRepositoryImpl
) {
    suspend operator fun invoke(productId: String): Flow<ResultState<ProductDescriptionResponse, String>> {
        return productsRepositoryImpl.getProductDescription(productId)
    }
}
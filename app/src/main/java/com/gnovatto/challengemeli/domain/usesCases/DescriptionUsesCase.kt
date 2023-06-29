package com.gnovatto.challengemeli.domain.usesCases

import com.gnovatto.challengemeli.domain.model.ProductDescriptionResponse
import com.gnovatto.challengemeli.domain.model.ResultState
import com.gnovatto.challengemeli.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DescriptionUsesCase @Inject constructor(
    private val productsRepository: ProductsRepository
){
    suspend operator fun invoke(productId: String): Flow<ResultState<ProductDescriptionResponse, String>> {
        return productsRepository.getProductDescription(productId)
    }
}
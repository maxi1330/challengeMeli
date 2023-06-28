package com.gnovatto.challengemeli.domain.usesCases

import com.gnovatto.challengemeli.domain.model.ResultState
import com.gnovatto.challengemeli.domain.model.ProductModel
import com.gnovatto.challengemeli.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsUsesCase @Inject constructor(
    private val productsRepository: ProductsRepository
){
    suspend operator fun invoke(query: String, page: Int): Flow<ResultState<List<ProductModel>, String>> {
        return productsRepository.getFilteredProducts(query, page)
    }

}
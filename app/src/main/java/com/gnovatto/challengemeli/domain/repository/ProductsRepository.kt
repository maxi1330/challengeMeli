package com.gnovatto.challengemeli.domain.repository

import com.gnovatto.challengemeli.domain.model.ProductModel
import com.gnovatto.challengemeli.domain.model.ResultState
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    /**
     * Obtiene una lista filtrada de productos.
     *
     * @param query La cadena de búsqueda.
     * @param page El número de página.
     * @return Un flujo de ResultState que contiene la lista de productos o un mensaje de error.
     */
    suspend fun getFilteredProducts(
        query: String,
        page: Int
    ): Flow<ResultState<List<ProductModel>, String>>

    /**
     * Obtiene el detalle de un producto.
     *
     * @param productId El ID del producto.
     * @return Un flow con ResultState que contiene el detalle del producto o un mensaje de error.
     */
    suspend fun getProductDetail(productId: String): Flow<ResultState<ProductModel, String>>
}
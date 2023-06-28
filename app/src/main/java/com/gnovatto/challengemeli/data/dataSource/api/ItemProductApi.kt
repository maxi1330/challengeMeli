package com.gnovatto.challengemeli.data.dataSource.api

import com.gnovatto.challengemeli.domain.model.ProductSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ItemProductApi {
    /**
     * Obtiene el detalle del producto buscado por ID
     * @param ID_PRODUCTO ID del producto para buscar
     */
    @GET("/items/{ID_PRODUCTO}")
    suspend fun getProductDetail(
        @Path("ID_PRODUCTO") productId: String,
    ): ProductSearchResponse
}
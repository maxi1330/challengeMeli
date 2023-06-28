package com.gnovatto.challengemeli.data.dataSource.api

import com.gnovatto.challengemeli.domain.model.ProductDescriptionResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DescriptionProductApi {

    /**
     * Obtiene la descripcion del producto buscado por ID de producto
     * @path Id del producto
     */
    @GET("/items/{ID_PRODUCTO}/description")
    suspend fun getProductsDescription(
        @Path("ID_PRODUCTO") productId: String,
    ): ProductDescriptionResponse

}
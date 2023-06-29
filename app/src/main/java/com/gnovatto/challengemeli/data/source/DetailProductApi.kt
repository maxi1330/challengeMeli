package com.gnovatto.challengemeli.data.source

import com.gnovatto.challengemeli.domain.model.ProductDescriptionResponse
import com.gnovatto.challengemeli.domain.model.ProductModel
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailProductApi {

    /**
     * Obtiene la descripcion del producto buscado por ID de producto
     * @path Id del producto
     */

    @Throws(Exception::class)
    @GET("/items/{ID_PRODUCT}/description")
    suspend fun getProductsDescription(
        @Path("ID_PRODUCT") productId: String,
    ): ProductDescriptionResponse


    /**
     * Obtiene el detalle de un producto buscado por ID de producto
     * @path Id del producto
     */

    @Throws(Exception::class)
    @GET("/items/{ID_PRODUCT}")
    suspend fun getDetailProduct(
        @Path("ID_PRODUCT") productId: String,
    ): ProductModel

}
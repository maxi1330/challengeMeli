package com.gnovatto.challengemeli.data.source

import com.gnovatto.challengemeli.domain.model.ProductSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductSearchApi {
    /**
    * Obtiene la lista de productos a partir de una consulta.
    *
    * @param query La consulta del producto a buscar.
    * @param page El número de página.
    * @param limit El límite de productos que se desea obtener.
    */
    @Throws(Exception::class)
    @GET("/sites/MLA/search")
    suspend fun getProducts(
        @Query("q") query: String,
        @Query("offset") page: Int,
        @Query("limit") limit: Int
    ): ProductSearchResponse
}
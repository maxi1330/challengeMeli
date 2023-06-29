package com.gnovatto.challengemeli.data.dataSource.api

import com.gnovatto.challengemeli.domain.model.ProductSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductSearchApi {
    /**
     * Obtiene la lista de productos a partir de la query
     * @param query query del producto para buscar
     * @param page pagina
     * @param limit limite de productos que se busca.
     */
    @Throws(Exception::class)
    @GET("/sites/MLA/search")
    suspend fun getProducts(
        @Query("q") query: String,
        @Query("offset") page: Int,
        @Query("limit") limit: Int
    ): ProductSearchResponse
}
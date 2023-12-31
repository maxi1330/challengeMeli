package com.gnovatto.challengemeli.domain.repository

import com.gnovatto.challengemeli.common.Constants
import com.gnovatto.challengemeli.createMock
import com.gnovatto.challengemeli.data.source.DetailProductApi
import com.gnovatto.challengemeli.data.source.ProductSearchApi
import com.gnovatto.challengemeli.data.repository.ProductsRepositoryImpl
import com.gnovatto.challengemeli.domain.model.ProductDescriptionResponse
import com.gnovatto.challengemeli.domain.model.ProductModel
import com.gnovatto.challengemeli.domain.model.ProductSearchResponse
import com.gnovatto.challengemeli.domain.model.ResultState
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsRepositoryImplTest {
    private lateinit var productsRepositoryImpl: ProductsRepositoryImpl

    @Mock
    private lateinit var provideSearchProductApi: ProductSearchApi

    @Mock
    private lateinit var provideDetailProductApi: DetailProductApi

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        productsRepositoryImpl = ProductsRepositoryImpl(
            provideSearchProductApi,
            provideDetailProductApi
        )
    }

    @Test
    fun `getFilteredProducts should return successful`() = runTest {
        val query = ""
        val page = 0
        val productList = mutableListOf<ProductModel>(
            createMock(), createMock(), createMock(),
        )

        val productSearchResponse = ProductSearchResponse(query, productList)

        Mockito.`when`(provideSearchProductApi.getProducts(anyString(), anyInt(), anyInt()))
            .thenReturn(productSearchResponse)

        val result = productsRepositoryImpl.getFilteredProducts(query, page)

        result.collect {
            assertTrue(it is ResultState.Success)
            assertEquals(productSearchResponse.results, (it as ResultState.Success).data)
            verify(provideSearchProductApi, times(1)).getProducts(anyString(), anyInt(), anyInt())
        }

    }

    @Test
    fun `getFilteredProducts should throw IOException`() = runTest {
        val query = ""
        val page = 0

        Mockito.`when`(provideSearchProductApi.getProducts(query, page, 20))
            .thenThrow(IOException::class.java)

        val result = productsRepositoryImpl.getFilteredProducts(query, page)

        result.collect {
            assertTrue(it is ResultState.Error)
            assertEquals(Constants.ERROR_IO_EXCEPTION, (it as ResultState.Error).message)
            verify(provideSearchProductApi, times(1)).getProducts(anyString(), anyInt(), anyInt())
        }
    }

    @Test
    fun `getFilteredProducts should throw HttpException`() = runTest {
        val query = ""
        val page = 0

        Mockito.`when`(provideSearchProductApi.getProducts(query, page, 20))
            .thenThrow(HttpException::class.java)

        val result = productsRepositoryImpl.getFilteredProducts(query, page)

        result.collect {
            assertTrue(it is ResultState.Error)
            assertEquals(Constants.ERROR_HTTP_EXCEPTION, (it as ResultState.Error).message)
            verify(provideSearchProductApi, times(1)).getProducts(anyString(), anyInt(), anyInt())
        }
    }

    @Test
    fun `getFilteredProducts should throw Exception`() = runTest {
        val query = ""
        val page = 0

        Mockito.`when`(provideSearchProductApi.getProducts(query, page, 20))
            .thenThrow(Exception::class.java)

        val result = productsRepositoryImpl.getFilteredProducts(query, page)

        result.collect {
            assertTrue(it is ResultState.Error)
            assertEquals(Constants.ERROR_HTTP_NOT_FOUND, (it as ResultState.Error).message)
            verify(provideSearchProductApi, times(1)).getProducts(anyString(), anyInt(), anyInt())
        }
    }

    @Test
    fun `getProductDetail should return successful`() = runTest {
        // Given
        val productId = "1234"
        val description = "Descripcion test"
        val product = ProductModel()
        val productDescriptionResponse = ProductDescriptionResponse(description,"1234","1234")

        Mockito.`when`(provideDetailProductApi.getDetailProduct(anyString()))
            .thenReturn(product)

        Mockito.`when`(provideDetailProductApi.getProductsDescription(anyString()))
            .thenReturn(productDescriptionResponse)

        // When
        val result = productsRepositoryImpl.getProductDetail(productId)

        // Then
        result.collect {
            assertTrue(it is ResultState.Success)
            assertEquals(description, (it as ResultState.Success).data.description)
            verify(provideDetailProductApi, times(1)).getDetailProduct(anyString())
        }

    }
    @Test
    fun `getProductDetail should throw IOException`() = runTest {
        // Given
        val productId = "1234"
        val description = "Descripcion test"
        val productDescriptionResponse = ProductDescriptionResponse(description,"1234","1234")

        Mockito.`when`(provideDetailProductApi.getDetailProduct(anyString()))
            .thenThrow(IOException::class.java)

        Mockito.`when`(provideDetailProductApi.getProductsDescription(anyString()))
            .thenReturn(productDescriptionResponse)

        // When
        val result = productsRepositoryImpl.getProductDetail(productId)

        // Then
        result.collect {
            assertTrue(it is ResultState.Error)
            assertEquals(Constants.ERROR_IO_EXCEPTION, (it as ResultState.Error).message)
            verify(provideDetailProductApi, times(1)).getDetailProduct(anyString())
        }
    }

    @Test
    fun `getProductDetail should throw HttpException`() = runTest {
        // Given
        val productId = "1234"
        val description = "Descripcion test"
        val productDescriptionResponse = ProductDescriptionResponse(description,"1234","1234")

        Mockito.`when`(provideDetailProductApi.getDetailProduct(anyString()))
            .thenThrow(HttpException::class.java)

        Mockito.`when`(provideDetailProductApi.getProductsDescription(anyString()))
            .thenReturn(productDescriptionResponse)

        // When
        val result = productsRepositoryImpl.getProductDetail(productId)

        // Then
        result.collect {
            assertTrue(it is ResultState.Error)
            assertEquals(Constants.ERROR_HTTP_EXCEPTION, (it as ResultState.Error).message)
            verify(provideDetailProductApi, times(1)).getDetailProduct(anyString())
        }
    }

    @Test
    fun `getProductDetail should throw exception`() = runTest {
        // Given
        val productId = "1234"
        val description = "Descripcion test"
        val productDescriptionResponse = ProductDescriptionResponse(description,"1234","1234")

        Mockito.`when`(provideDetailProductApi.getDetailProduct(anyString()))
            .thenThrow(Exception::class.java)

        Mockito.`when`(provideDetailProductApi.getProductsDescription(anyString()))
            .thenReturn(productDescriptionResponse)

        // When
        val result = productsRepositoryImpl.getProductDetail(productId)

        // Then
        result.collect {
            assertTrue(it is ResultState.Error)
            assertEquals(Constants.ERROR_HTTP_NOT_FOUND, (it as ResultState.Error).message)
            verify(provideDetailProductApi, times(1)).getDetailProduct(anyString())
        }
    }

}
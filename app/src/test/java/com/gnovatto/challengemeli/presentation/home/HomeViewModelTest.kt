package com.gnovatto.challengemeli.presentation.home

import com.gnovatto.challengemeli.TestLogger
import com.gnovatto.challengemeli.common.Logger
import com.gnovatto.challengemeli.createMock
import com.gnovatto.challengemeli.domain.model.ProductModel
import com.gnovatto.challengemeli.domain.model.ResultState
import com.gnovatto.challengemeli.domain.usesCases.ProductsUsesCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.util.ReflectionHelpers.setField

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private lateinit var homeViewModel: HomeViewModel

    private val dispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var productsUsesCase: ProductsUsesCase

    private val logger: Logger = TestLogger()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        homeViewModel = HomeViewModel(
            productsUsesCase,
            dispatcher
        )
        setField(homeViewModel, "logger", logger)

    }

    @Test
    fun `getMoreProducts should return NewProducts`() = runTest(dispatcher) {
        // Given
        val query = "pelota"
        val page = 0
        val productList = mutableListOf<ProductModel>(createMock(), createMock(), createMock())

        Mockito.`when`(productsUsesCase.invoke("pelota", page)).thenReturn(
            flowOf(ResultState.Success(productList))
        )

        // When
        homeViewModel.getMoreProducts(query)
        runCurrent()

        // Then
        assertEquals(
            HomeState.NewProducts(productList),
            homeViewModel.uiStateHome.value
        )
    }

    @Test
    fun `getMoreProducts should return MoreProducts`() = runTest(dispatcher) {
        // Given
        val query = ""
        val page = 1
        val productList = mutableListOf<ProductModel>(createMock(), createMock(), createMock())

        Mockito.`when`(productsUsesCase.invoke("", page)).thenReturn(
            flowOf(ResultState.Success(productList))
        )

        // When
        homeViewModel.getMoreProducts(query)
        runCurrent()

        // Then
        assertEquals(
            HomeState.MoreProducts(productList),
            homeViewModel.uiStateHome.value
        )
    }

    @Test
    fun `getMoreProducts should return Error`() = runTest(dispatcher) {
        // Given
        val query = ""
        val page = 1
        val message = "Error test"

        Mockito.`when`(productsUsesCase.invoke("", page)).thenReturn(
            flowOf(ResultState.Error(message))
        )

        // When
        homeViewModel.getMoreProducts(query)
        runCurrent()

        // Then
        assertEquals(
            HomeState.Error(message),
            homeViewModel.uiStateHome.value
        )
    }

}
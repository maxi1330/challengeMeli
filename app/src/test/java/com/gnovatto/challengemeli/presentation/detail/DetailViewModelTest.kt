package com.gnovatto.challengemeli.presentation.detail

import com.gnovatto.challengemeli.TestLogger
import com.gnovatto.challengemeli.common.Logger
import com.gnovatto.challengemeli.createMock
import com.gnovatto.challengemeli.domain.model.ProductModel
import com.gnovatto.challengemeli.domain.model.ResultState
import com.gnovatto.challengemeli.domain.usesCases.DetailUsesCase
import junit.framework.TestCase
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
import org.robolectric.util.ReflectionHelpers


@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private lateinit var detailViewModel: DetailViewModel

    private val dispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var descriptionUsesCase: DetailUsesCase

    private val logger: Logger = TestLogger()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        detailViewModel = DetailViewModel(
            descriptionUsesCase,
            dispatcher
        )
        ReflectionHelpers.setField(detailViewModel, "logger", logger)
    }

    @Test
    fun `getDescription should return DetailState Detail`() = runTest(dispatcher) {
        // Given
        val productId = "1234"

        val productModel = createMock<ProductModel>()

        Mockito.`when`(descriptionUsesCase.invoke(productId)).thenReturn(
            flowOf(ResultState.Success(productModel))
        )

        // When
        detailViewModel.getDetail(productId)
        runCurrent()

        // Then
        TestCase.assertEquals(
            DetailState.Detail(productModel),
            detailViewModel.uiStateDetail.value
        )
    }

    @Test
    fun `getDescription should return DetailState Error`() = runTest(dispatcher) {
        // Given
        val productId = "1234"
        val messageError = "Error Test"

        Mockito.`when`(descriptionUsesCase.invoke(productId)).thenReturn(
            flowOf(ResultState.Error(messageError))
        )

        // When
        detailViewModel.getDetail(productId)
        runCurrent()

        // Then
        TestCase.assertEquals(
            DetailState.Error(messageError),
            detailViewModel.uiStateDetail.value
        )
    }
}
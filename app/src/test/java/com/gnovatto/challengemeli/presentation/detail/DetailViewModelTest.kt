package com.gnovatto.challengemeli.presentation.detail

import com.gnovatto.challengemeli.TestLogger
import com.gnovatto.challengemeli.common.Logger
import com.gnovatto.challengemeli.createMock
import com.gnovatto.challengemeli.domain.model.ProductDescriptionResponse
import com.gnovatto.challengemeli.domain.model.ProductModel
import com.gnovatto.challengemeli.domain.model.ResultState
import com.gnovatto.challengemeli.domain.usesCases.DescriptionUsesCase
import com.gnovatto.challengemeli.domain.usesCases.ProductsUsesCase
import com.gnovatto.challengemeli.presentation.home.HomeState
import com.gnovatto.challengemeli.presentation.home.HomeViewModel
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.util.ReflectionHelpers


@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private lateinit var detailViewModel: DetailViewModel

    private val dispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var descriptionUsesCase: DescriptionUsesCase

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
    fun `getDescription should return DetailState Description`() = runTest(dispatcher) {
        // Given
        val productId = "1234"
        val description = "Descripcion Test"
        val lastUpdated = "1234"
        val dateCreated = "1234"

        Mockito.`when`(descriptionUsesCase.invoke(productId)).thenReturn(
            flowOf(ResultState.Success(ProductDescriptionResponse(description,lastUpdated,dateCreated)))
        )

        // When
        detailViewModel.getDescription(productId)
        runCurrent()

        // Then
        TestCase.assertEquals(
            DetailState.Description(description),
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
        detailViewModel.getDescription(productId)
        runCurrent()

        // Then
        TestCase.assertEquals(
            DetailState.Error(messageError),
            detailViewModel.uiStateDetail.value
        )
    }
}
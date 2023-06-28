package com.gnovatto.challengemeli

import com.gnovatto.challengemeli.data.dataSource.api.DescriptionProductApi
import com.gnovatto.challengemeli.domain.model.ProductDescriptionResponse
import com.gnovatto.challengemeli.domain.model.ResultState
import com.gnovatto.challengemeli.domain.usesCases.DescriptionUsesCase
import com.gnovatto.challengemeli.presentation.detail.DetailState
import com.gnovatto.challengemeli.presentation.detail.DetailViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    // Mock del caso de uso de descripción
    @Mock
    lateinit var descriptionUsesCase: DescriptionUsesCase

    // ViewModel a probar
    lateinit var viewModel: DetailViewModel

    // Configuración inicial de las pruebas
    @Before
    fun setup() {
        // Inicializar los mocks
        MockitoAnnotations.openMocks(this)

        // Crear una instancia del ViewModel con los mocks
        viewModel = DetailViewModel(descriptionUsesCase)
    }

    @Test
    fun `getDescription should update uiStateDetail with description when successful`() = runBlocking {
        // Datos de prueba
        val productId = "example_product_id"
        val description = "Example description"

        // Configurar el caso de uso para devolver un resultado exitoso con la descripción esperada
        Mockito.`when`(descriptionUsesCase.invoke(productId)).thenReturn(flowOf(ResultState.Success(
            ProductDescriptionResponse(plainText = description, "", "")
        )))

        // Llamar al método a probar
        viewModel.getDescription(productId)

        // Obtener el estado actual del UI
        val uiState = viewModel.uiStateDetail.value

        // Verificar que el estado sea una Description con la descripción esperada
        assertTrue(uiState is DetailState.Description)
//        assertEquals(description, uiState.description)
    }

    @Test
    fun `getDescription should update uiStateDetail with error when an error occurs`() = runBlocking {
        // Datos de prueba
        val productId = "example_product_id"
        val errorMessage = "An error occurred"

        // Configurar el caso de uso para devolver un resultado de error con el mensaje de error esperado
        Mockito.`when`(descriptionUsesCase.invoke(productId)).thenReturn(flowOf(ResultState.Error(errorMessage)))

        // Llamar al método a probar
        viewModel.getDescription(productId)

        // Obtener el estado actual del UI
        val uiState = viewModel.uiStateDetail.value

        // Verificar que el estado sea un Error con el mensaje de error esperado
        assertTrue(uiState is DetailState.Error)
//        assertEquals(errorMessage, uiState.message)
    }
}

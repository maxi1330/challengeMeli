package com.gnovatto.challengemeli.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gnovatto.challengemeli.common.Logger
import com.gnovatto.challengemeli.common.LoggerImpl
import com.gnovatto.challengemeli.domain.model.ProductModel
import com.gnovatto.challengemeli.domain.model.ResultState
import com.gnovatto.challengemeli.domain.usesCases.DetailUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val descriptionUsesCase: DetailUsesCase,
    @Named("dispatcher") private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiStateDetail = MutableStateFlow<DetailState>(DetailState.Loading(false))
    val uiStateDetail: StateFlow<DetailState> = _uiStateDetail

    private val logger : Logger = LoggerImpl

    /**
     * Obtiene los detalles de un producto.
     *
     * @param productId El ID del producto.
     */
    fun getDetail(productId: String) {
        logger.debug("productId: $productId")
        viewModelScope.launch(dispatcher) {
            descriptionUsesCase
                .invoke(productId)
                .onStart { showLoading(true) }
                .catch { error ->
                    showLoading(false)
                    showError("")
                }
                .collect { response ->
                    showLoading(false)
                    when (response) {
                        is ResultState.Success -> {
                            logger.debug("Detail: ${response.data}")
                            setDetail(response.data)
                        }

                        is ResultState.Error -> showError(response.message)
                    }
                }
        }
    }

    private fun showError(message: String) {
        _uiStateDetail.value = DetailState.Error(message)
    }

    private fun showLoading(loading: Boolean) {
        _uiStateDetail.value = DetailState.Loading(loading)
    }

    private fun setDetail(detail: ProductModel) {
        _uiStateDetail.value = DetailState.Detail(detail)
    }
}

/**
 * Representa el estado de la UI.
 */
sealed class DetailState {
    data class Loading(val isLoading: Boolean) : DetailState()
    data class Detail(val detail: ProductModel) : DetailState()
    data class Error(val message: String) : DetailState()
}
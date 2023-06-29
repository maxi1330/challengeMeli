package com.gnovatto.challengemeli.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gnovatto.challengemeli.common.Logger
import com.gnovatto.challengemeli.domain.model.ResultState
import com.gnovatto.challengemeli.domain.usesCases.DescriptionUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val descriptionUsesCase: DescriptionUsesCase,
) : ViewModel() {

    private val _uiStateDetail = MutableStateFlow<DetailState>(DetailState.Loading(false))
    val uiStateDetail: StateFlow<DetailState> = _uiStateDetail

    fun getDescription(productId: String){
        Logger.debug("productId: $productId")
        viewModelScope.launch {
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
                            Logger.debug("Descripcion: ${response.data.plainText}")
                            setDescription(response.data.plainText)
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

    private fun setDescription(description: String) {
        _uiStateDetail.value = DetailState.Description(description)
    }
}

sealed class DetailState {
    data class Loading(val isLoading: Boolean) : DetailState()
    data class Description(val description: String) : DetailState()
    data class Error(val message: String) : DetailState()
}
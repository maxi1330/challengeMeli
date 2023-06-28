package com.gnovatto.challengemeli.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gnovatto.challengemeli.common.ResultState
import com.gnovatto.challengemeli.domain.model.ProductModel
import com.gnovatto.challengemeli.domain.usesCases.ProductsUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productsUsesCase: ProductsUsesCase,
    ) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeState>(HomeState.Loading(false))
    val uiState: StateFlow<HomeState> = _uiState

    private var lastQuery = ""
    private var page = 0
    fun getMoreProducts(query: String){
        viewModelScope.launch {
            validateQuery(query)
            productsUsesCase
                .invoke(query, page)
                .onStart { showLoading(true) }
                .catch { error ->
                    showLoading(false)
                    showError("")
                }
                .collect { response ->
                    showLoading(false)
                    when (response) {
                        is ResultState.Success -> {
                            if(page == 0){
                                newProducts(response.data)
                            } else {
                                moreProducts(response.data)
                            }
                        }
                        is ResultState.Error -> showError(response.msjError)
                    }
                }
        }
    }

    private fun validateQuery(query: String){
        if (lastQuery.lowercase() != query.lowercase()){
            reset(query)
        } else {
            page++
        }
    }

    private fun reset(query: String){
        page = 0
        lastQuery = query
    }

    private fun showError(message: String) {
        _uiState.value = HomeState.Error(message)
    }

    private fun showLoading(loading: Boolean) {
        _uiState.value = HomeState.Loading(loading)
    }

    private fun moreProducts(products: List<ProductModel>) {
        _uiState.value = HomeState.MoreProducts(products)
    }

    private fun newProducts(products: List<ProductModel>) {
        _uiState.value = HomeState.NewProducts(products)
    }

}

sealed class HomeState {
    data class Loading(val isLoading: Boolean) : HomeState()
    data class NewProducts(val products: List<ProductModel>) : HomeState()
    data class MoreProducts(val products: List<ProductModel>) : HomeState()
    data class Error(val message: String) : HomeState()
}
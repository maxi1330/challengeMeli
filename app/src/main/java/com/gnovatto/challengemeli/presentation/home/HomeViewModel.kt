package com.gnovatto.challengemeli.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gnovatto.challengemeli.common.ResultState
import com.gnovatto.challengemeli.domain.model.ProductModel
import com.gnovatto.challengemeli.domain.model.ProductsDTO
import com.gnovatto.challengemeli.domain.usesCases.ProductsUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productsUsesCase: ProductsUsesCase,
    ) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState(isLoading = true))
    val uiState: StateFlow<HomeState> = _uiState

    private var productList = mutableListOf<ProductModel>()
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
                        is ResultState.Success -> addProductsToList(response.data)
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
        productList = emptyList<ProductModel>().toMutableList()
    }

    private fun showError(message: String) {
        _uiState.value = HomeState(errorMessage = message);
    }

    private fun showLoading(loading: Boolean) {
        _uiState.value = HomeState(isLoading = loading);
    }

    private fun addProductsToList(products: List<ProductModel>) {
        productList.addAll(products)
        _uiState.value = HomeState(productList = productList)
    }

}

data class HomeState(
    val isLoading: Boolean = false,
    val isNewQuery: Boolean = true,
    val newProducts: Boolean = false,
    val errorMessage: String = "",
    val productList: List<ProductModel> = emptyList()
)
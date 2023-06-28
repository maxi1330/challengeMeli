package com.gnovatto.challengemeli.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gnovatto.challengemeli.common.Logger
import com.gnovatto.challengemeli.domain.model.ResultState
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

    private val _uiStateHome = MutableStateFlow<HomeState>(HomeState.Loading(false))
    val uiStateHome: StateFlow<HomeState> = _uiStateHome

    private var lastQuery = ""
    private var page = 0
    fun getMoreProducts(query: String){
        validateQuery(query)
        Logger.debug("query: $query")
        Logger.debug("lastQuery: $lastQuery")
        Logger.debug("page: $page")
        viewModelScope.launch {
            productsUsesCase
                .invoke(lastQuery, page)
                .onStart { showLoading(true) }
                .catch { error ->
                    showLoading(false)
                    showError("")
                }
                .collect { response ->
                    showLoading(false)
                    when (response) {
                        is ResultState.Success -> {
                            Logger.debug(response.data.toString())
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
        if (query.isNotEmpty() && lastQuery.lowercase() != query.lowercase()){
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
        _uiStateHome.value = HomeState.Error(message)
    }

    private fun showLoading(loading: Boolean) {
        _uiStateHome.value = HomeState.Loading(loading)
    }

    private fun moreProducts(products: List<ProductModel>) {
        _uiStateHome.value = HomeState.MoreProducts(products)
    }

    private fun newProducts(products: List<ProductModel>) {
        _uiStateHome.value = HomeState.NewProducts(products)
    }

}

sealed class HomeState {
    data class Loading(val isLoading: Boolean) : HomeState()
    data class NewProducts(val products: List<ProductModel>) : HomeState()
    data class MoreProducts(val products: List<ProductModel>) : HomeState()
    data class Error(val message: String) : HomeState()
}
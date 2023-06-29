package com.gnovatto.challengemeli.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gnovatto.challengemeli.common.Logger
import com.gnovatto.challengemeli.common.LoggerImpl
import com.gnovatto.challengemeli.domain.model.ProductModel
import com.gnovatto.challengemeli.domain.model.ResultState
import com.gnovatto.challengemeli.domain.usesCases.ProductsUsesCase
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
class HomeViewModel @Inject constructor(
    private val productsUsesCase: ProductsUsesCase,
    @Named("dispatcher") private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiStateHome = MutableStateFlow<HomeState>(HomeState.Loading(false))
    val uiStateHome: StateFlow<HomeState> = _uiStateHome

    private val logger : Logger = LoggerImpl
    private var lastQuery = ""
    private var page = 0
    fun getMoreProducts(query: String) {
        validateQuery(query)
        logger.debug("query: $query")
        logger.debug("lastQuery: $lastQuery")
        logger.debug("page: $page")

        viewModelScope.launch(dispatcher) {
            productsUsesCase(lastQuery, page)
                .onStart { showLoading(true) }
                .catch { e ->
                    showLoading(false)
                    showError(e.message.toString())
                }
                .collect { response ->
                    showLoading(false)
                    when (response) {
                        is ResultState.Success -> {
                            logger.debug(response.data.toString())
                            if (page == 0) {
                                newProducts(response.data)
                            } else {
                                moreProducts(response.data)
                            }
                        }

                        is ResultState.Error -> showError(response.message)
                    }
                }
        }
    }

    private fun validateQuery(query: String) {
        if (query.isNotEmpty() && lastQuery.lowercase() != query.lowercase()) {
            reset(query)
        } else {
            page++
        }
    }

    private fun reset(query: String) {
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
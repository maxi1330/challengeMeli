package com.gnovatto.challengemeli.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gnovatto.challengemeli.common.Logger
import com.gnovatto.challengemeli.common.LoggerImpl
import com.gnovatto.challengemeli.databinding.FragmentHomeBinding
import com.gnovatto.challengemeli.domain.model.ProductModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), ProductsAdapter.OnItemClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var productsAdapter: ProductsAdapter

    private lateinit var viewModel: HomeViewModel
    private var isLoadingMoreItems = false

    private val logger : Logger = LoggerImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        setRecyclerProducts()
        setObservers()
        setSearch()
        return binding.root
    }

    private fun setRecyclerProducts() {
        productsAdapter = ProductsAdapter(this)
        binding.recyclerViewProducts.adapter = productsAdapter
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(
            context,
            (binding.recyclerViewProducts.layoutManager as LinearLayoutManager).orientation
        )
        binding.recyclerViewProducts.addItemDecoration(dividerItemDecoration)

        binding.recyclerViewProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                val isAtEnd = (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                val isNotLoading = !isLoadingMoreItems

                if (isAtEnd && isNotLoading) {
                    logger.debug("Se piden mas productos")
                    isLoadingMoreItems = true
                    viewModel.getMoreProducts("")
                }
            }
        })
    }

    private fun setSearch() {
        _binding?.searchProduct?.setOnEditorActionListener { searchText, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(requireContext(), requireView())
                logger.debug("Query seleccionada: ${searchText.text}")
                viewModel.getMoreProducts(searchText.text.toString())
                true
            } else {
                false
            }
        }
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiStateHome.collect { state ->
                when (state) {
                    is HomeState.Loading -> {
                        resetErrorViews()
                        showLoading(state.isLoading)
                        logger.debug("Loading: ${state.isLoading}")
                    }

                    is HomeState.NewProducts -> {
                        productsAdapter.setNewProductList(state.products)
                        isLoadingMoreItems = false
                        logger.debug("Productos nuevos")
                    }

                    is HomeState.MoreProducts -> {
                        productsAdapter.setMoreProductList(state.products)
                        isLoadingMoreItems = false
                        logger.debug("Agregar productos")
                    }

                    is HomeState.Error -> {
                        showError(state.message)
                        isLoadingMoreItems = false
                    }
                }
            }
        }
    }

    private fun showError(message: String) {
        binding.recyclerViewProducts.visibility = View.GONE
        binding.msjError.text = message
        binding.messageError.visibility = View.VISIBLE
    }

    private fun resetErrorViews() {
        binding.messageError.visibility = View.GONE
        binding.recyclerViewProducts.visibility = View.VISIBLE
    }

    private fun showLoading(loading: Boolean) {
        binding.progressBarHome.visibility = if (loading) View.VISIBLE else View.GONE
        binding.messageError.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun hideKeyboard(context: Context, view: View) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    override fun onItemClick(product: ProductModel) {
        logger.debug("Item seleccionado: ${product.title}")
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(product)
        findNavController().navigate(action)
    }

}



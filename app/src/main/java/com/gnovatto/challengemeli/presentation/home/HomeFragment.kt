package com.gnovatto.challengemeli.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gnovatto.challengemeli.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        setObservers()
        setSearch()
        return binding.root
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is HomeState.Loading -> {
                        resetViews()
                        showLoading(state.isLoading)
                    }
                    is HomeState.NewProducts -> {
                        // Manejar el estado de nuevos productos
                        displayNewProducts(state.products)
                    }
                    is HomeState.MoreProducts -> {
                        // Manejar el estado de más productos
                        displayMoreProducts(state.products)
                    }
                    is HomeState.Error -> {
                        // Manejar el estado de error
                        showError(state.message)
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

    private fun resetViews(){
        binding.messageError.visibility = View.GONE
        binding.recyclerViewProducts.visibility = View.VISIBLE
    }

    private fun showLoading(loading: Boolean) {
        binding.progressBarHome.visibility = if(loading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setSearch(){
        _binding?.searchProduct?.setOnEditorActionListener { text, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.getMoreProducts(text.toString())
                true
            } else {
                false
            }
        }
    }

}


//                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
//                findNavController().navigate(action)
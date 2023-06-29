package com.gnovatto.challengemeli.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.gnovatto.challengemeli.R
import com.gnovatto.challengemeli.common.Logger
import com.gnovatto.challengemeli.common.LoggerImpl
import com.gnovatto.challengemeli.common.Utils
import com.gnovatto.challengemeli.common.extensions.formatPrice
import com.gnovatto.challengemeli.databinding.FragmentDetailBinding
import com.gnovatto.challengemeli.domain.model.ProductModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()

    private lateinit var viewModel: DetailViewModel

    private val logger : Logger = LoggerImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        setObservers()
        setToolbar()
        viewModel.getDetail(args.product.id)
        logger.debug("Ingreso pantalla detalle")
        return binding.root
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiStateDetail.collect { state ->
                when (state) {
                    is DetailState.Loading -> {
                        showLoading(state.isLoading)
                        logger.debug("Loading: ${state.isLoading}")
                    }

                    is DetailState.Detail -> {
                        setProductData(state.detail)
                        logger.debug("Descripcion ok")
                    }

                    is DetailState.Error -> {
                        showError()
                    }
                }
            }
        }
    }

    private fun showError() {
        binding.description.text = getString(R.string.msj_error_description)
    }

    private fun showLoading(loading: Boolean) {
        binding.progressBarDetail.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun setProductData(product : ProductModel) {
        with(product) {
            binding.condition.text = Utils.formatCondition(condition)
            binding.soldQuantity.text = Utils.formatSold(soldQuantity)
            binding.title.text = title
            binding.price.text = price.formatPrice(currencyId)
            binding.stockAvailable.text = availableQuantity.toString()
            binding.description.text = description.ifEmpty { getString(R.string.not_description) }
            Glide.with(requireContext())
                .load(if (pictures.isNotEmpty()) pictures[0].url else thumbnail)
                .into(binding.imageProductDetail)
            binding.groupLabels.visibility= View.VISIBLE
        }
        showLoading(false)
    }

    private fun setToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.title_detail_page)

        binding.toolbar.setNavigationIcon(R.drawable.arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }


}
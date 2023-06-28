package com.gnovatto.challengemeli.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.gnovatto.challengemeli.R
import com.gnovatto.challengemeli.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!
    val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        setToolbar()
        setProductData()
        return binding.root
    }

    private fun setProductData() {
        binding.condition.text = if (args.product.condition.lowercase() == "new") "Nuevo" else "Usado"
        binding.soldQuantity.text = "${args.product.soldQuantity} vendidos"
        binding.title.text = args.product.title
        binding.price.text = "${args.product.currencyId} ${args.product.price}"
        binding.stockAvailable.text = args.product.availableQuantity.toString()
        Glide.with(requireContext())
            .load(args.product.thumbnail)
            .into(binding.imageProductDetail)
    }

    private fun setToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Producto"

        binding.toolbar.setNavigationIcon(R.drawable.arrow_left)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}
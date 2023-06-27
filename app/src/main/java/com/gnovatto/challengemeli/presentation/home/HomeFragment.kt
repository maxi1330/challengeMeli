package com.gnovatto.challengemeli.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.gnovatto.challengemeli.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        setSearch()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setSearch(){
        val navController = findNavController()
        _binding?.searchProduct?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                navController.navigate(action)
                true
            } else {
                Toast.makeText(context,"false",Toast.LENGTH_SHORT).show()
                false
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {

            }
    }
}
package com.example.myProductApp.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marsrealestate.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment() {
    private lateinit var viewModel: OverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[OverviewViewModel::class.java]
        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val glm = GridLayoutManager(requireContext(), 2)
        glm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val currentList = viewModel.displayList.value ?: emptyList()

                return when (currentList[position]) {
                    is ListItem.CategoryHeader -> 2
                    is ListItem.ProductItem -> 1
                    else -> 1
                }
            }
        }
        binding.photosGrid.layoutManager = glm

        val adapter = PhotoGridAdapter { product ->
            viewModel.displayProductDetails(product)
        }
        binding.photosGrid.adapter = adapter

        viewModel.displayList.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToSelectedProduct.observe(viewLifecycleOwner, Observer { product ->
            product?.let {
                findNavController().navigate(
                    OverviewFragmentDirections.actionOverviewFragmentToDetailsFragment(
                        it
                    )
                )
                viewModel.displayProductDetailsComplete()
            }
        })

        return binding.root
    }
}

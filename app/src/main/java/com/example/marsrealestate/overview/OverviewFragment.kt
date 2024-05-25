package com.example.marsrealestate.overview

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.marsrealestate.R
import com.example.marsrealestate.databinding.FragmentOverviewBinding
import com.example.marsrealestate.network.MarsApiFilter


class OverviewFragment : Fragment() {
    private lateinit var viewModel: OverviewViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel=ViewModelProvider(this)[OverviewViewModel::class.java]
        val binding = FragmentOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.photosGrid.adapter = PhotoGridAdapter{
            viewModel.displayPropertyDetails(it)
        }
        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController()
                    .navigate(OverviewFragmentDirections.actionOverviewFragmentToDetailsFragment(it))
                 viewModel.displayPropertyDetailsComplete()
            }
        })
        fun setButtonColors(showAllColor: Int, rentColor: Int, buyColor: Int) {
            binding.showAllBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), showAllColor))
            binding.rentBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), rentColor))
            binding.buyBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), buyColor))
        }

        binding.showAllBtn.setOnClickListener {
            viewModel.updateFilter(MarsApiFilter.SHOW_ALL)
            setButtonColors(R.color.brown, R.color.card, R.color.card)
        }

        binding.rentBtn.setOnClickListener {
            viewModel.updateFilter(MarsApiFilter.RENT)
            setButtonColors(R.color.card, R.color.brown, R.color.card)
        }

        binding.buyBtn.setOnClickListener {
            viewModel.updateFilter(MarsApiFilter.BUY)
            setButtonColors(R.color.card, R.color.card, R.color.brown)
        }

        return binding.root
    }

}


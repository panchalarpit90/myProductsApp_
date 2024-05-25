package com.example.marsrealestate.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.marsrealestate.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentDetailsBinding.inflate(inflater)
        val marsProperty=DetailsFragmentArgs.fromBundle(requireArguments()).selectedProperty

        val viewModelFactory=DetailsViewModelFactory(marsProperty,application)
        binding.viewModel= ViewModelProvider(this,viewModelFactory)[DetailsViewModel::class.java]
        binding.lifecycleOwner = this
        return binding.root
    }
}

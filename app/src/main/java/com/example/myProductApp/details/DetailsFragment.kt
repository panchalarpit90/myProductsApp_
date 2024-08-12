package com.example.myProductApp.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.marsrealestate.R
import com.example.marsrealestate.databinding.FragmentDetailsBinding
import com.example.myProductApp.network.Product
import com.example.myProductApp.network.Review

class DetailsFragment : Fragment() {
    private var binding: FragmentDetailsBinding? = null
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var viewPager2: ViewPager2
    private lateinit var dotsLayout: LinearLayout
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val application = requireNotNull(activity).application
        val selectedProduct = DetailsFragmentArgs.fromBundle(requireArguments()).selectedProduct

        val viewModelFactory = DetailsViewModelFactory(selectedProduct, application)
        binding?.viewModel = ViewModelProvider(this, viewModelFactory)[DetailsViewModel::class.java]
        binding?.lifecycleOwner = this
        binding?.iconImage?.setOnClickListener {
            findNavController().popBackStack()
        }
        setupImageSlider(selectedProduct)
        setupReviewRecyclerView(selectedProduct.reviews)

        return binding?.root
    }

    private fun setupImageSlider(product: Product) {
        imageAdapter = ImageAdapter()
        viewPager2 = binding?.root?.findViewById(R.id.viewpager2) ?: return
        dotsLayout = binding?.root?.findViewById(R.id.slideDotLL) ?: return

        viewPager2.adapter = imageAdapter
        imageAdapter.submitList(product.images)

        setupDotsIndicator(product.images.size)
        setupPageChangeListener()
    }

    private fun setupReviewRecyclerView(reviews: List<Review>) {
        reviewAdapter = ReviewAdapter()
        binding?.root?.findViewById<RecyclerView>(R.id.review_recycler_view)?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = reviewAdapter
        }
        reviewAdapter.submitList(reviews)
    }

    private fun setupDotsIndicator(size: Int) {
        val dotsImage = Array(size) { ImageView(requireContext()) }

        dotsImage.forEach {
            it.setImageResource(R.drawable.non_active_dot)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(8, 0, 8, 0)
            }
            dotsLayout.addView(it, params)
        }

        dotsImage[0].setImageResource(R.drawable.active_dot)

        pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                dotsImage.mapIndexed { index, imageView ->
                    imageView.setImageResource(
                        if (position == index) R.drawable.active_dot
                        else R.drawable.non_active_dot
                    )
                }
                super.onPageSelected(position)
            }
        }

        viewPager2.registerOnPageChangeCallback(pageChangeListener)
    }

    private fun setupPageChangeListener() {
        viewPager2.registerOnPageChangeCallback(pageChangeListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager2.unregisterOnPageChangeCallback(pageChangeListener)
    }
}

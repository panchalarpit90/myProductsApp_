package com.example.myProductApp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.marsrealestate.R
import com.example.myProductApp.overview.ListItem
import com.example.myProductApp.overview.PhotoGridAdapter
import com.example.myProductApp.overview.StatusApi

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ListItem>?) {
    val adapter = recyclerView.adapter as? PhotoGridAdapter
    adapter?.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.animated_rotate_vector)
                    .error(R.drawable.broken_image)
            )
            .into(imgView)
    }
}

@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: StatusApi?) {
    when (status) {
        StatusApi.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.error_image)
        }

        else -> {
            statusImageView.visibility = View.GONE
        }
    }
}


@BindingAdapter("availabilityStatus")
fun setAvailabilityStatusColor(view: TextView, status: String?) {
    val color = when (status) {
        "In Stock" -> R.color.color_in_stock // Green
        else -> R.color.color_out_of_stock
    }
    view.setTextColor(view.context.getColor(color))
}

@BindingAdapter("shimmerVisibility")
fun bindShimmerVisibility(
    shimmerLayout: com.facebook.shimmer.ShimmerFrameLayout,
    status: StatusApi?
) {
    when (status) {
        StatusApi.LOADING -> {
            shimmerLayout.visibility = View.VISIBLE
            shimmerLayout.startShimmer()
        }

        else -> {
            shimmerLayout.visibility = View.GONE
            shimmerLayout.stopShimmer()
        }
    }
}


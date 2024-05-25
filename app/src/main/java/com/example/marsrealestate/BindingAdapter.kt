package com.example.marsrealestate

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.marsrealestate.network.MarsProperty
import com.example.marsrealestate.overview.PhotoGridAdapter
import com.example.marsrealestate.overview.StatusApi


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MarsProperty>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri=imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                        .placeholder(R.drawable.animated_rotate_vector)
                        .error(R.drawable.broken_image))
            .into(imgView)
    }
}


@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, status: StatusApi?) {
    when (status) {
        StatusApi.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.animated_rotate_vector)
        }
        StatusApi.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.error_image)
        }

        null -> statusImageView.visibility = View.GONE
    }
}
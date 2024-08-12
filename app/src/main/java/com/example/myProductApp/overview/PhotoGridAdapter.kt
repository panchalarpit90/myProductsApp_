package com.example.myProductApp.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marsrealestate.databinding.CategoryFilterItemBinding
import com.example.marsrealestate.databinding.GridItemViewBinding
import com.example.myProductApp.network.Product

sealed class ListItem {
    data class CategoryHeader(val category: String) : ListItem()
    data class ProductItem(val product: Product) : ListItem()
}

class PhotoGridAdapter(private val onClickListener: (Product) -> Unit) :
    ListAdapter<ListItem, RecyclerView.ViewHolder>(DiffUtilCallback()) {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.CategoryHeader -> VIEW_TYPE_HEADER
            is ListItem.ProductItem -> VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = CategoryFilterItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HeaderViewHolder(binding)
            }

            VIEW_TYPE_ITEM -> {
                val binding =
                    GridItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ProductViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val header = getItem(position) as ListItem.CategoryHeader
                holder.bind(header)
            }

            is ProductViewHolder -> {
                val productItem = getItem(position) as ListItem.ProductItem
                holder.bind(productItem.product)
                holder.itemView.setOnClickListener {
                    onClickListener(productItem.product)
                }
            }
        }
    }

    class HeaderViewHolder(private val binding: CategoryFilterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(header: ListItem.CategoryHeader) {
            binding.category = header.category
            binding.executePendingBindings()
        }
    }

    class ProductViewHolder(private val binding: GridItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
            binding.executePendingBindings()
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }
    }
}

package com.example.marsrealestate.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marsrealestate.databinding.GridItemViewBinding
import com.example.marsrealestate.network.MarsProperty


class PhotoGridAdapter(private val onClickListener: (MarsProperty) -> Unit) : ListAdapter<MarsProperty, PhotoGridAdapter.MarsPropertyViewHolder>(DiffUtilCallback()) {

    class MarsPropertyViewHolder(private val binding: GridItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(marsProperty: MarsProperty) {
            binding.property = marsProperty
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPropertyViewHolder {
        return MarsPropertyViewHolder(GridItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener(marsProperty)
        }
        holder.bind(marsProperty)
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<MarsProperty>() {
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }
    }
}

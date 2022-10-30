package com.bendg.bg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bendg.bg.data.remote.model.CategoryModel
import com.bendg.bg.data.remote.model.CategoryTypes
import com.bendg.bg.databinding.ItemCategoryBinding

class CategoryAdapter :
    ListAdapter<CategoryTypes, CategoryAdapter.CategoryViewHolder>(ItemCallback) {

    var onItemClickListener: ((CategoryTypes) -> Unit)? = null

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val item = getItem(adapterPosition)
            binding.apply {
                tvCategory.text = item.value
                root.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoryAdapter.CategoryViewHolder = CategoryViewHolder(ItemCategoryBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false))

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) =
        holder.bind()

    object ItemCallback : DiffUtil.ItemCallback<CategoryTypes>() {
        override fun areItemsTheSame(
            oldItem: CategoryTypes,
            newItem: CategoryTypes,
        ): Boolean {
            return oldItem.value == newItem.value
        }

        override fun areContentsTheSame(
            oldItem: CategoryTypes,
            newItem: CategoryTypes,
        ): Boolean {
            return oldItem == newItem
        }
    }
}
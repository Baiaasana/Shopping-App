package com.bendg.bg.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bendg.bg.databinding.SingleItemBinding
import com.bendg.bg.presenter.model.ItemUI
import com.bendg.bg.utility.Glide

class ProductsAdapter :
    ListAdapter<ItemUI.Product, ProductsAdapter.ProductsViewHolder>(ItemCallback) {

    var onItemClickListener: ((ItemUI.Product) -> Unit)? = null

    inner class ProductsViewHolder(private val binding: SingleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val item = getItem(adapterPosition)
            binding.apply {
                tvTitle.text = item.title.toString()
                tvPrice.text = item.price.toString().plus(" $")
                Glide().setImage(item.thumbnail.toString(), ivItem)
                root.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder =
        ProductsViewHolder(SingleItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) = holder.bind()

    object ItemCallback : DiffUtil.ItemCallback<ItemUI.Product>() {
        override fun areItemsTheSame(
            oldItem: ItemUI.Product,
            newItem: ItemUI.Product,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ItemUI.Product,
            newItem: ItemUI.Product,
        ): Boolean {
            return oldItem == newItem
        }
    }

}
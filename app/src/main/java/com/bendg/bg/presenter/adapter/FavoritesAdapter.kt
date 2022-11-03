package com.bendg.bg.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bendg.bg.data.local.model.FavoriteProduct
import com.bendg.bg.databinding.FavoriteItemBinding
import com.bendg.bg.utility.Glide

class FavoritesAdapter :
    ListAdapter<FavoriteProduct, FavoritesAdapter.FavoritesViewHolder>(FavoriteItemCallBack) {

    var onItemClickListener: ((FavoriteProduct) -> Unit)? = null

    inner class FavoritesViewHolder(private val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val item = getItem(adapterPosition)
            binding.apply {
                Glide().setImage(item.image, binding.ivItem)
                tvTitle.text = item.title.toString()
                tvPrice.text = item.price.toString().plus(" $")
                root.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoritesViewHolder(
            FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind()
    }

    private object FavoriteItemCallBack : DiffUtil.ItemCallback<FavoriteProduct>() {
        override fun areItemsTheSame(oldItem: FavoriteProduct, newItem: FavoriteProduct): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FavoriteProduct,
            newItem: FavoriteProduct,
        ): Boolean {
            return oldItem == newItem
        }
    }
}
package com.bendg.bg.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bendg.bg.databinding.CartItemBinding
import com.bendg.bg.presenter.model.CartModel
import com.bendg.bg.utility.Glide

class CartAdapter : ListAdapter<CartModel, CartAdapter.CartViewHolder>(ItemCallback) {

    var counter = 0

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val item = getItem(adapterPosition)
            binding.apply {
                Glide().setImage(item.image, ivCart)
                tvItemName.text = item.title
                tvPrice.text = item.price.toString()

                ivPlus.setOnClickListener {
                    counter++
                    tvCounter.text = counter.toString()
                }

                ivMinus.setOnClickListener {
                    if (counter != 0) {
                        counter--
                        tvCounter.text = counter.toString()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CartAdapter.CartViewHolder = CartViewHolder(
        CartItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) =
        holder.bind()

    object ItemCallback : DiffUtil.ItemCallback<CartModel>() {
        override fun areItemsTheSame(
            oldItem: CartModel,
            newItem: CartModel,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CartModel,
            newItem: CartModel,
        ): Boolean {
            return oldItem == newItem
        }
    }
}

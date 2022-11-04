package com.bendg.bg.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bendg.bg.databinding.CartItemBinding
import com.bendg.bg.presenter.model.CartModel
import com.bendg.bg.utility.Glide
import javax.security.auth.callback.Callback

class CartAdapter : ListAdapter<CartModel, CartAdapter.CartViewHolder>(ItemCallback) {

//    var onPlusClick: ((CartModel) -> Int)? = null

//    var onMinusClick: ((CartModel) -> Int)? = null

    private var callback: Callback ?= null

    interface Callback{

        fun onPlusClick(itemID:Int)

        fun onMinusCLick(itemID: Int)
    }

    fun setCallback(callback: Callback){
        this.callback = callback

    }

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val item = getItem(adapterPosition)
            binding.apply {
                Glide().setImage(item.image, ivCart)
                tvItemName.text = item.title
                tvPrice.text = item.price.toString()

                ivPlus.setOnClickListener {
//                    onPlusClick?.invoke(item)
                    callback?.onPlusClick(item.id!!)
                    tvCounter.text = item.counter.toString()

                }

                ivMinus.setOnClickListener {
                    if (item.counter > 0) {
                        callback?.onMinusCLick(item.id!!)
                        tvCounter.text = item.counter.toString()
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
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: CartModel,
            newItem: CartModel,
        ): Boolean {
            return oldItem == newItem
        }
    }
}

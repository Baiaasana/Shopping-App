package com.bendg.bg.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bendg.bg.R
import com.bendg.bg.data.local.model.OrderedProduct
import com.bendg.bg.databinding.OrderItemBinding
import com.bendg.bg.utility.Glide

class OrderAdapter :
    ListAdapter<OrderedProduct, OrderAdapter.OrdersViewHolder>(OrderItemCallBack) {

    var onItemClickListener: ((OrderedProduct) -> Unit)? = null

    inner class OrdersViewHolder(private val binding: OrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val item = getItem(adapterPosition)
            binding.apply {
                Glide().setImage(item.image, binding.ivOrder)
                tvTitle.text = item.title.toString()
                ivRemove.setImageResource(R.drawable.ic_favorite_true)
                tvPrice.text = item.price.toString().plus(" $")
                ivRemove.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        OrdersViewHolder(
            OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        holder.bind()
    }

    private object OrderItemCallBack : DiffUtil.ItemCallback<OrderedProduct>() {
        override fun areItemsTheSame(oldItem: OrderedProduct, newItem: OrderedProduct): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: OrderedProduct,
            newItem: OrderedProduct,
        ): Boolean {
            return oldItem == newItem
        }
    }
}
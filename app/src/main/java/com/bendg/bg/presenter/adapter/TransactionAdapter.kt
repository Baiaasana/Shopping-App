package com.bendg.bg.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bendg.bg.R
import com.bendg.bg.databinding.ItemTransactionBinding
import com.bendg.bg.presenter.model.UserModel
import com.bendg.bg.utility.Glide

class TransactionAdapter :
    ListAdapter<UserModel.Transaction, TransactionAdapter.TransactionViewHolder>(OrdersItemCallBack) {

    var onItemClickListener: ((UserModel.Transaction) -> Unit)? = null

    inner class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val item = getItem(adapterPosition)
            binding.apply {
                Glide().setImage(item.image, binding.ivOrder)
                tvTitle.text = item.title.toString()
                ivRemove.setImageResource(R.drawable.ic_favorite_true)
                tvPrice.text = item.price.toString().plus(" $")
                tvDate.text = item.date.toString()
                ivRemove.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TransactionViewHolder(
            ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind()
    }

    private object OrdersItemCallBack : DiffUtil.ItemCallback<UserModel.Transaction>() {
        override fun areItemsTheSame(
            oldItem: UserModel.Transaction,
            newItem: UserModel.Transaction,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserModel.Transaction,
            newItem: UserModel.Transaction,
        ): Boolean {
            return oldItem == newItem
        }
    }
}

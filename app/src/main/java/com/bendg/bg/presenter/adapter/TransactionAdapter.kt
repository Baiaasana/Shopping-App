package com.bendg.bg.presenter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bendg.bg.R
import com.bendg.bg.databinding.ItemTransactionBinding
import com.bendg.bg.presenter.model.UserModel
import com.bendg.bg.common.extensions.Glide
import java.text.SimpleDateFormat
import java.util.*

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
                ivRemove.setImageResource(R.drawable.ic_baseline_restore_from_trash_24)
                tvPrice.text = item.price.toString().plus(" $")
                tvDate.text = item.date.let { getData(it!!.toLong(), "yyyy-MM-dd").toString() }
                ivRemove.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getData(milliSeconds: Long, dateFormat: String?): String? {
        val formatter = SimpleDateFormat(dateFormat)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
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

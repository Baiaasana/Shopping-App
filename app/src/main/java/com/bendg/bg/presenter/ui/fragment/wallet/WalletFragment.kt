package com.bendg.bg.presenter.ui.fragment.wallet

import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentWalletBinding
import com.bendg.bg.presenter.adapter.TransactionAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding>(FragmentWalletBinding::inflate) {

    private val viewModel: WalletViewModel by viewModels()
    private val transactionAdapter: TransactionAdapter = TransactionAdapter()

    private val db: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("userInfo")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun listeners() {
        transactionAdapter.onItemClickListener = { trans ->
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.transactionsFlow.collect {
                    val oldList = it.data
                    oldList!!.remove(trans)
                    viewModel.updateUserInfo(oldList)
                    viewModel.showTransactions()
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.transactionsFlow.collect {
                    transactionAdapter.submitList(it.data)
                }
            }
        }
    }

    override fun init() {
        getTransactions()
        getMoney(binding.tvPrice)
        getNumber(binding.tvCardNumber)
        getUserName(binding.tvUserName)
        initRecycler()
    }

    private fun initRecycler() {
        binding.rvTransactions.apply {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun getTransactions() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.showTransactions()
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.transactionsFlow.collect {
                val data = it.data
                transactionAdapter.submitList(data)
            }
        }
    }

    private fun getMoney(tvPrice: TextView) {
        db.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                val balance = it.child("cards").child("balance").value
                tvPrice.text = balance.toString().plus(" $")
            }
        }
    }

    private fun getNumber(tvCardNumber: TextView) {
        db.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                val cardNumber = it.child("cards").child("cardNumber").value
                tvCardNumber.text = cardNumber.toString()
            }
        }
    }

    private fun getUserName(tvUserName: TextView) {
        db.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                val cardNumber = it.child("userName").value
                tvUserName.text = cardNumber.toString()
            }
        }
    }

}
package com.bendg.bg.presenter.ui.fragment.wallet

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentWalletBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding>(FragmentWalletBinding::inflate) {

    private val viewModel: WalletViewModel by viewModels()

    private val db: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("userInfo")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun listeners() {}

    override fun init() {
        getMoney(binding.tvPrice)
        getNumber(binding.tvCardNumber)
        getUserName(binding.tvUserName)
    }

    override fun observers() {}

    @SuppressLint("SetTextI18n")
    private fun getMoney(tvPrice: TextView) {
        db.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
            if (it.exists()) {
                val balance = it.child("cards").child("balance").value
                tvPrice.text = balance.toString() + "$"
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
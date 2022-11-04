package com.bendg.bg.presenter.ui.fragment.checkout

import android.annotation.SuppressLint
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentCheckoutBinding
import com.bendg.bg.utility.snack
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutFragment : BaseFragment<FragmentCheckoutBinding>(FragmentCheckoutBinding::inflate) {

    private val args: CheckoutFragmentArgs by navArgs()
    private val db: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("userInfo")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun listeners() {
        binding.btnPayment.setOnClickListener {
            when {
                isCheckPayment() -> it.snack("please check payment method")

                else -> {
                    db.child(auth.currentUser?.uid!!).get().addOnSuccessListener {
                        if (it.exists()) {
                            val balance = it.child("cards").child("balance").value
                            if (balance.toString().toInt() < args.totalMoney) {
                                Snackbar.make(
                                    binding.root,
                                    "Please fill the balance",
                                    Snackbar.LENGTH_LONG
                                )
                                    .show()
                            } else {
                                val newBalance = balance.toString().toInt().minus(args.totalMoney)
                                db.child(auth.currentUser?.uid!!).child("cards").child("balance")
                                    .setValue(newBalance)
                                navigate()
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun init() {
        binding.apply {
            tvTotal.text = args.totalMoney.toString() + "$"
        }
    }

    override fun observers() {}

    private fun isCheckPayment(): Boolean {
        return !binding.checkBox.isChecked
    }

    private fun navigate() {
        findNavController().navigate(CheckoutFragmentDirections.actionCheckoutFragmentToPaymentFragment())
    }
}
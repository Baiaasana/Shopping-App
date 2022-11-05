package com.bendg.bg.presenter.ui.fragment.checkout

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.data.local.model.OrderedProduct
import com.bendg.bg.databinding.FragmentCheckoutBinding
import com.bendg.bg.presenter.model.CartModel
import com.bendg.bg.utility.cartList
import com.bendg.bg.utility.snack
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CheckoutFragment : BaseFragment<FragmentCheckoutBinding>(FragmentCheckoutBinding::inflate) {

    private val viewModel: CheckoutViewModel by viewModels()

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
                            if (balance.toString().toInt() < binding.tvTotal.text.toString().toInt()) {
                                Snackbar.make(
                                    binding.root,
                                    "Please fill the balance",
                                    Snackbar.LENGTH_LONG
                                )
                                    .show()
                            } else {
                                val newBalance = balance.toString().toInt()
                                    .minus(binding.tvTotal.text.toString().toInt())
                                db.child(auth.currentUser?.uid!!).child("cards").child("balance")
                                    .setValue(newBalance)
                                navigate()
                            }
                            Log.d("log", "cartsList- ".plus(cartList))
                            val orderList = getOrder(cartList)
                            Log.d("log", "orderList- ".plus(orderList))
                            orderList.forEach { orderProduct ->
                                viewLifecycleOwner.lifecycleScope.launch {
                                    viewModel.addProduct(orderProduct)
                                    Log.d("log", "orderProduct - ".plus(orderProduct))
                                }
                            }
                            cartList.removeAll(cartList)
                            Log.d("log", "clear cartsList- ".plus(cartList))
                        }
                    }
                }
            }
        }
    }

    private fun getOrder(cartList: List<CartModel>): List<OrderedProduct> {
        val orderList = mutableListOf<OrderedProduct>()
        for (item in cartList) {
            orderList.add(OrderedProduct(
                id = item.id,
                title = item.title,
                price = item.price,
                image = item.image,
            ))
        }
        return orderList
    }


    @SuppressLint("SetTextI18n")
    override fun init() {
        binding.apply {
            tvSubtotal.text = args.totalMoney.toString()
            tvTotal.text =
                (args.totalMoney + binding.tvFee.text.toString().toInt()).toString()

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
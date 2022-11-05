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

    private val args: CheckoutFragmentArgs by navArgs()

    private val viewModel: CheckoutViewModel by viewModels()

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

                            // add order
                            Log.d("log", "cart list - ".plus(cartList))
                            val orderList = getOrders(cartList)
                            Log.d("log", "order list - ".plus(orderList))

                            orderList.forEach { orderedProduct ->
                                viewLifecycleOwner.lifecycleScope.launch {
                                    Log.d("log", "order product - ".plus(orderedProduct))
                                    viewModel.addOrder(orderedProduct)
                                }
                            }
                            cartList.removeAll(cartList)
                            Log.d("log", "cart list - ".plus(cartList))
                        }
                    }
                }
            }
        }
    }

    private fun getOrders(cartList: List<CartModel>): List<OrderedProduct> {
        val orderList = mutableListOf<OrderedProduct>()
        for (item in cartList) {
            orderList.add(OrderedProduct(
                id = item.id!!.toInt(),
                title = item.title,
                price = item.price,
                image = item.image,
                date = System.currentTimeMillis()
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
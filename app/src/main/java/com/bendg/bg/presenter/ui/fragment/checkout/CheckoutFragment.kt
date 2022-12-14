package com.bendg.bg.presenter.ui.fragment.checkout

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bendg.bg.R
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.data.local.model.OrderedProduct
import com.bendg.bg.databinding.FragmentCheckoutBinding
import com.bendg.bg.presenter.model.CartModel
import com.bendg.bg.presenter.model.UserModel
import com.bendg.bg.extensions.cartList
import com.bendg.bg.extensions.snack
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

    private val database: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("userInfo")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun listeners() {
        binding.btnPayment.setOnClickListener {
            when {
                isCheckPayment() -> it.snack(getString(R.string.check_method))
                else -> {
                    database.child(auth.currentUser?.uid!!).get().addOnSuccessListener { snapshot ->
                        if (snapshot.exists()) {
                            val balance = snapshot.child(getString(R.string.cards)).child(getString(
                                                            R.string.balance)).value
                            if (balance.toString().toInt() < binding.tvTotal.text.toString()
                                    .toInt()
                            ) {
                                Snackbar.make(
                                    binding.root,
                                    getString(R.string.fill_balance),
                                    Snackbar.LENGTH_LONG
                                )
                                    .show()
                            } else {
                                val newBalance = balance.toString().toInt()
                                    .minus(binding.tvTotal.text.toString().toInt())
                                database.child(auth.currentUser?.uid!!).child(getString(R.string.cards))
                                    .child(getString(R.string.balance))
                                    .setValue(newBalance)
                                navigate()
                                saveOrders()
                                saveTransactions()
                                cartList.removeAll(cartList)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun saveTransactions() {
        val transactionList = getTransactions(cartList)
        viewModel.showTransactions()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.transactionsFlow.collect { transactions ->
                val oldList = transactions.data
                oldList!!.addAll(transactionList)
                database.child(auth.currentUser?.uid!!)
                    .child("transactions")
                    .setValue(oldList)
            }
        }
    }

    private fun saveOrders() {
        val orderList = getOrders(cartList)
        orderList.forEach { orderedProduct ->
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.addOrder(orderedProduct)
            }
        }
    }

    private fun getOrders(cartList: List<CartModel>): List<OrderedProduct> {
        val orderList = mutableListOf<OrderedProduct>()
        for (item in cartList) {
            orderList.add(
                OrderedProduct(
                    id = item.id!!.toInt(),
                    title = item.title,
                    price = item.price,
                    image = item.image,
                    date = System.currentTimeMillis()
                )
            )
        }
        return orderList
    }

    private fun getTransactions(cartList: List<CartModel>): List<UserModel.Transaction> {
        val transactionList = mutableListOf<UserModel.Transaction>()
        for (item in cartList) {
            transactionList.add(
                UserModel.Transaction(
                    id = item.id!!.toInt(),
                    title = item.title,
                    price = item.price,
                    image = item.image,
                    date = System.currentTimeMillis(),
                    counter = item.counter
                )
            )
        }
        return transactionList
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
package com.bendg.bg.presenter.ui.fragment.carts

import android.annotation.SuppressLint
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentCartsBinding
import com.bendg.bg.presenter.adapter.CartAdapter
import com.bendg.bg.utility.cartList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartsFragment : BaseFragment<FragmentCartsBinding>(FragmentCartsBinding::inflate) {


    private val cartsAdapter = CartAdapter()

    private val sum = cartList.sumOf { it.price!! }

    override fun listeners() {
        binding.btnCheckout.setOnClickListener {
            findNavController().navigate(
                CartsFragmentDirections.actionCartsFragmentToCheckoutFragment(
                    totalMoney = sum
                )
            )
        }
    }

    @SuppressLint("SetTextI18n")
    override fun init() {
        initRecycle()
        cartsAdapter.submitList(cartList)

        binding.tvPrice.text = "$sum$"

    }

    override fun observers() {
    }

    private fun initRecycle() {
        binding.rvCarts.apply {
            adapter = cartsAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

}
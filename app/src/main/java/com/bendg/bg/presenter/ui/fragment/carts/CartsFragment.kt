package com.bendg.bg.presenter.ui.fragment.carts

import android.annotation.SuppressLint
import android.util.Log.d
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentCartsBinding
import com.bendg.bg.presenter.adapter.CartAdapter
import com.bendg.bg.utility.cartList
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class CartsFragment : BaseFragment<FragmentCartsBinding>(FragmentCartsBinding::inflate) {


    private val cartsAdapter = CartAdapter()

    private var sum = 0

    override fun listeners() {
        binding.btnCheckout.setOnClickListener {
            findNavController().navigate(
                CartsFragmentDirections.actionCartsFragmentToCheckoutFragment(
                    totalMoney = sum
                )
            )
        }

        cartsAdapter.onPlusClick = {
            it.counter++
            totalSum()
            binding.tvPrice.text = "$sum$"
        }

        cartsAdapter.onMinusClick = {
            it.counter--
            totalSum()
            binding.tvPrice.text = "$sum$"
        }
    }

    private fun totalSum() {
        cartList.forEach {
            sum += it.counter * it.price!!
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
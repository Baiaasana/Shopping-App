package com.bendg.bg.presenter.ui.fragment.carts

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

    private var sum = cartList.sumOf { it.price!! }

    override fun listeners() {
        binding.btnCheckout.setOnClickListener {
            findNavController().navigate(
                CartsFragmentDirections.actionCartsFragmentToCheckoutFragment(
                    totalMoney = sum
                )
            )
        }
    }

    override fun init() {
        initRecycle()
        cartsAdapter.submitList(cartList)
        binding.tvPrice.text = sum.toString()

        cartsAdapter.setCallback(object : CartAdapter.Callback{
            override fun onPlusClick(itemID: Int) {
               plus(itemID)
                    var totalsum = 0
                    for(x in cartList){
                        sum = x.counter * x.price!!
                        totalsum += sum
                    }
                    binding.tvPrice.text = totalsum.toString()
            }

            override fun onMinusCLick(itemID: Int) {
                minus(itemID)
                var totalsum = 0
                for(x in cartList){
                    sum = x.counter * x.price!!
                    totalsum -= sum
                }
                binding.tvPrice.text = totalsum.toString()
            }
        })
    }

    private fun minus(itemID:Int){
        val item = cartList.find { item ->
            item.id == itemID
        }
        val index = cartList.indexOf(item)
        cartList[index].counter--
        cartsAdapter.submitList(cartList.toList())
    }

    private fun plus(itemID: Int){
        val item = cartList.find { item ->
            item.id == itemID
        }
        val index = cartList.indexOf(item)
        cartList[index].counter++
        cartsAdapter.submitList(cartList.toList())
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
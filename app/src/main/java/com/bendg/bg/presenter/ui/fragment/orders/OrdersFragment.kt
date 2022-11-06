package com.bendg.bg.presenter.ui.fragment.orders

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bendg.bg.R
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentOrdersBinding
import com.bendg.bg.presenter.adapter.OrdersAdapter
import com.bendg.bg.utility.cartList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>(FragmentOrdersBinding::inflate) {

    private val viewModel: OrdersViewModel by viewModels()

    private val ordersAdapter: OrdersAdapter = OrdersAdapter()

    override fun listeners() {

        ordersAdapter.onItemClickListener = {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.removeOrder(it)
                Log.d("log", "remove 1 order - ".plus(viewModel.ordersFlow.value.data))
                viewModel.getOrders()
                Log.d("log", "remove  2 order - ".plus(viewModel.ordersFlow.value.data))
            }
            observers()
        }
    }

    override fun init() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getOrders()
            Log.d("log", "get order - ".plus(viewModel.ordersFlow.value))

        }

        binding.rvOrders.apply {
            adapter = ordersAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.ordersFlow.collect{
                ordersAdapter.submitList(it.data)
                Log.d("log", "get order - ".plus(viewModel.ordersFlow.value.data))
            }
        }
    }
}
package com.bendg.bg.presenter.ui.fragment.orders

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentOrdersBinding
import com.bendg.bg.presenter.adapter.OrderAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>(FragmentOrdersBinding::inflate) {

    private var orderAdapter: OrderAdapter = OrderAdapter()
    private val viewModel: OrdersViewModel by viewModels()

    override fun listeners() {

        orderAdapter.onItemClickListener = {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.removeOrder(it)
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.getOrders()
                }
                observers()
            }
        }
    }

    override fun init() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getOrders()
        }

        binding.rvOrders.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.ordersFlow.collect {
                orderAdapter.submitList(it.data)
            }
        }

    }
}
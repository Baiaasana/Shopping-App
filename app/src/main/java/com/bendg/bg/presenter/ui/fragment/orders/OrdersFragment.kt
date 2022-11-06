package com.bendg.bg.presenter.ui.fragment.orders

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.data.local.model.OrderedProduct
import com.bendg.bg.databinding.FragmentOrdersBinding
import com.bendg.bg.presenter.adapter.OrdersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>(FragmentOrdersBinding::inflate) {

    private val viewModel: OrdersViewModel by viewModels()

    private val ordersAdapter: OrdersAdapter = OrdersAdapter()

    override fun listeners() {
        ordersAdapter.onItemClickListener = {
            removeProduct(it)
            observers()
        }
    }

    private fun removeProduct(order: OrderedProduct) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.removeOrder(order)
            viewModel.getOrders()
        }
    }

    override fun init() {
        getOrders()
        initRecycler()
    }

    private fun getOrders() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getOrders()
        }
    }

    private fun initRecycler() {
        binding.rvOrders.apply {
            adapter = ordersAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.ordersFlow.collect {
                ordersAdapter.submitList(it.data)
            }
        }
    }
}
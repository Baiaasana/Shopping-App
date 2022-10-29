package com.bendg.bg.presenter.ui.fragment.home

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bendg.bg.adapter.ProductsAdapter
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private val productsAdapter: ProductsAdapter = ProductsAdapter()

    override fun listeners() {
        productsAdapter.onItemClickListener = {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(id = it.id!!.toInt()))
        }
    }

    override fun init() {
        viewModel.getProductsInfo()
        initRecycler()
    }

    private fun initRecycler(){
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = productsAdapter
        }
    }
    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.productsFlow.collect{
                    productsAdapter.submitList(it.data)
                }
            }
        }

    }

}
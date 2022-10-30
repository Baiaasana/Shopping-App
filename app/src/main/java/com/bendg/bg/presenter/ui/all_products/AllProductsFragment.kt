package com.bendg.bg.presenter.ui.all_products

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bendg.bg.adapter.CategoryAdapter
import com.bendg.bg.adapter.ProductsAdapter
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.data.remote.model.CategoryTypes
import com.bendg.bg.databinding.FragmentAllProductsBinding
import com.bendg.bg.presenter.ui.fragment.home.HomeFragmentDirections
import com.bendg.bg.presenter.ui.fragment.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllProductsFragment : BaseFragment<FragmentAllProductsBinding>(FragmentAllProductsBinding::inflate) {

    private val viewModel: AllProductsViewModel by viewModels()
    private val productsAdapter: ProductsAdapter = ProductsAdapter()
    private val categoryAdapter: CategoryAdapter = CategoryAdapter()

    override fun listeners() {
        productsAdapter.onItemClickListener = {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(id = it.id!!.toInt()))
        }
    }

    override fun init() {
        initRecycler()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getProductsInfo()
        }
    }

    private fun initRecycler(){
        binding.rvAllProducts.apply {
            adapter = productsAdapter
        }
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
        buildRecycler()

    }
    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.productsFlow.collect{
                    binding.progressBar.isVisible = it.isLoading
                    productsAdapter.submitList(it.data)
                }
            }
        }
    }

    private fun buildRecycler(){
        categoryAdapter.submitList(CategoryTypes.values().toList())
    }
}
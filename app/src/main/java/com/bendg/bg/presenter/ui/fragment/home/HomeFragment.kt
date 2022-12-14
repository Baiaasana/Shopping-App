package com.bendg.bg.presenter.ui.fragment.home

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.data.remote.model.CategoryTypes
import com.bendg.bg.databinding.FragmentHomeBinding
import com.bendg.bg.presenter.adapter.CategoryAdapter
import com.bendg.bg.presenter.adapter.ProductsAdapter
import com.bendg.bg.presenter.model.ItemUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private val productsAdapter: ProductsAdapter = ProductsAdapter()
    private val categoryAdapter: CategoryAdapter = CategoryAdapter()

    override fun listeners() {
        binding.tvSeeAll.setOnClickListener {
            navigateToAllProducts()
        }
        binding.etSearch.setOnClickListener {
            navigateToSearch()
        }
        productsAdapter.onItemClickListener = {
            navigateToDetails(it)
        }
        categoryAdapter.onItemClickListener = {
            setCategory(it)
        }
    }

    private fun setCategory(category: CategoryTypes) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.setCategory(category)
        }
    }

    private fun navigateToDetails(product: ItemUI.Product) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                id = product.id!!.toInt()
            )
        )
    }

    private fun navigateToSearch() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
    }

    private fun navigateToAllProducts() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAllProductsFragment2())
    }

    override fun init() {
        initRecycler()
    }

    private fun initRecycler() {
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = productsAdapter
        }
        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
        buildCategoryRecycler()
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productsFlow.collect {
                    binding.progressBar.isVisible = it.isLoading
                    productsAdapter.submitList(it.data)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.categoryFlow.collect {
                getProductsByCategory(category = it.value)
            }
        }
    }

    private fun buildCategoryRecycler() {
        categoryAdapter.submitList(CategoryTypes.values().toList())
    }

    private fun getProductsByCategory(category: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getProductsByCategory(
                category = category
            )
        }
    }
}
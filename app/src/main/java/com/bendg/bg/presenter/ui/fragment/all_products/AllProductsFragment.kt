package com.bendg.bg.presenter.ui.fragment.all_products

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bendg.bg.presenter.adapter.ProductsAdapter
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentAllProductsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllProductsFragment : BaseFragment<FragmentAllProductsBinding>(FragmentAllProductsBinding::inflate) {

    private val viewModel: AllProductsViewModel by viewModels()
    private val productsAdapter: ProductsAdapter = ProductsAdapter()

    override fun listeners() {
        productsAdapter.onItemClickListener = {
            findNavController().navigate(AllProductsFragmentDirections.actionAllProductsFragmentToDetailsFragment(id = it.id!!.toInt()))
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
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = productsAdapter
        }
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
}
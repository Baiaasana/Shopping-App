package com.bendg.bg.presenter.ui.fragment.search

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentSearchBinding
import com.bendg.bg.presenter.adapter.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels()
    private val productsAdapter: ProductsAdapter = ProductsAdapter()

    override fun listeners() {
        productsAdapter.onItemClickListener = {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
                id = it.id!!.toInt()))
        }
        binding.btnSearch.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getProductsBySearch(binding.etSearch.text.toString())
                observe()
            }

        }
    }

    override fun init() {
        initRecycler()
    }

    private fun initRecycler() {
        binding.rvSearch.apply {
            adapter = productsAdapter
        }
    }

    override fun observers() {

    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productsFlow.collect {
                    productsAdapter.submitList(it.data)
                    Log.d("log", "fragment data - ${it.data}")

                }
            }
        }
    }
}
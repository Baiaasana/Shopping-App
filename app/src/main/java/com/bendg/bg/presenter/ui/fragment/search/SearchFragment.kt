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
import com.bendg.bg.presenter.model.ItemUI
import com.bendg.bg.common.extensions.hideKeyboard
import com.bendg.bg.common.extensions.showKeyboardFor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels()
    private val productsAdapter: ProductsAdapter = ProductsAdapter()

    override fun listeners() {
        productsAdapter.onItemClickListener = {
            navigateToDetails(it)
        }
        binding.apply {
            btnSearch.setOnClickListener {
                when {
                    isEmptyField() -> return@setOnClickListener
                    else -> search()
                }
                hideKeyboard()
            }
            btnBack.setOnClickListener {
                findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToHomeFragment())
            }
        }
    }

    private fun navigateToDetails(product: ItemUI.Product) {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
            id = product.id!!.toInt()))
    }

    private fun search() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getProductsBySearch(binding.etSearch.text.toString())
            observe()
        }
    }

    private fun hideKeyboard() {
        requireActivity().hideKeyboard()
    }

    private fun isEmptyField(): Boolean = with(binding) {
        return@with etSearch.text.toString().isEmpty()
    }

    override fun init() {
        showKeyBoard()
        initRecycler()
    }

    private fun showKeyBoard() {
        binding.apply {
            etSearch.requestFocusFromTouch()
            requireContext().showKeyboardFor(etSearch)
        }
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
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
import com.bendg.bg.utility.extensions.hideKeyboard
import com.bendg.bg.utility.extensions.showKeyboardFor
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
        binding.apply {
            btnSearch.setOnClickListener {
                when{
                    isEmptyField() -> return@setOnClickListener
                    else -> {
                        viewLifecycleOwner.lifecycleScope.launch {
                            viewModel.getProductsBySearch(binding.etSearch.text.toString())
                            observe()
                        }
                    }
                }
                requireActivity().hideKeyboard()
            }
            btnBack.setOnClickListener {
                findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToHomeFragment())
            }
        }
    }

    private fun isEmptyField(): Boolean = with(binding) {
        return@with etSearch.text.toString().isEmpty()
    }
    override fun init() {
        binding.apply {
            etSearch.requestFocusFromTouch()
            requireContext().showKeyboardFor(etSearch)
        }
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
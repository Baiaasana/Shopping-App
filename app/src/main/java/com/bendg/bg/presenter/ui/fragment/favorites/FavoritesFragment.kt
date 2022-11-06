package com.bendg.bg.presenter.ui.fragment.favorites

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentFavoritesBinding
import com.bendg.bg.presenter.adapter.FavoritesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel: FavoritesViewModel by viewModels()
    private val favoriteAdapter: FavoritesAdapter = FavoritesAdapter()

    override fun listeners() {
        adapterListener()
    }

    override fun init() {
        initRecycler()
        getFavorites()
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoritesFlow.collect {
                favoriteAdapter.submitList(it.data)
            }
        }
    }

    private fun adapterListener() {
        favoriteAdapter.onItemClickListener = {
            findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(
                id = it.id ?: 1))
        }
    }

    private fun getFavorites() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFavorites()
        }
    }

    private fun initRecycler() {
        binding.rvFavorites.apply {
            adapter = favoriteAdapter
        }
    }

}
package com.bendg.bg.presenter.ui.fragment.favorites

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
        favoriteAdapter.onItemClickListener = {
            findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(
                id = it.id ?: 1))
        }
    }

    override fun init() {
        binding.rvFavorites.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = favoriteAdapter
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFavorites()
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoritesFlow.collect{
                favoriteAdapter.submitList(it.data)
                Log.d("log", "fav freagment".plus(it.data))
                Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

}
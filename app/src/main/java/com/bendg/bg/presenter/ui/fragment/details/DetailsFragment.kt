package com.bendg.bg.presenter.ui.fragment.details

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun listeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToHomeFragment())
        }
    }

    override fun init() {
        viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getProductsInfo(id = args.id)
                Log.d("log", " args id ${args.id}")
        }
    }

    override fun observers() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.detailedFlow.collect {
//                    val result = it.data as ItemUI.Product
//                    binding.apply {
//                        tvTitle.text = result.title.toString()
//                        tvPrice.text = result.price.toString()
//                        tvBrand.text = result.brand.toString()
//                        tvDescription.text = result.description.toString()
//                        tvRating.text = result.rating.toString()
//                        Glide().setImage(result.thumbnail.toString(), ivItem)
//                        Log.d("log", "success data ${result}")
//
//                    }
//                }
//            }
//        }
    }
}
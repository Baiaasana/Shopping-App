package com.bendg.bg.presenter.ui.fragment.details

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bendg.bg.R
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.data.local.model.FavoriteProduct
import com.bendg.bg.databinding.FragmentDetailsBinding
import com.bendg.bg.presenter.model_ui.ProductModelUi
import com.bendg.bg.utility.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    private var isSaved = false
    private lateinit var image: String
    private var price by Delegates.notNull<Int>()


    override fun listeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToHomeFragment())
        }
        binding.ivSetFavorite.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                saveProduct(getProduct())
                isSaved = !isSaved
                Log.d("log", "details fragment ".plus(isSaved.toString()))
                Log.d("log", "details fragment ".plus(viewModel.favoritesFlow.value))

            }
        }
    }

    private suspend fun saveProduct(product: FavoriteProduct) {
        when (isSaved) {
            true -> {
                viewModel.removeProduct(product)
                binding.ivSetFavorite.setImageResource(R.drawable.ic_baseline_settings_24)
            }
            false -> {
                viewModel.addProduct(product)
                binding.ivSetFavorite.setImageResource(R.drawable.ic_arrow_left)
            }
        }
    }

    override fun init() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getProductById(id = args.id)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getFavorites()
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.detailedFlow.collect {
                if (it.data != null) {
                    val result = it.data as ProductModelUi
                    image = result.thumbnail ?: ""
                    price = result.price.toString().toInt()
                    binding.apply {
                        tvTitle.text = result.title.toString()
                        tvPrice.text = result.price.toString()
                        tvBrand.text = result.brand.toString()
                        tvDescription.text = result.description.toString()
                        tvRating.text = result.rating.toString()
                        Glide().setImage(result.thumbnail, ivItem)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoritesFlow.collect {
                val product = it.find { product ->
                    product.id == args.id
                }
                product.let {
                    isSaved = true
                    binding.ivSetFavorite.setImageResource(R.drawable.ic_arrow_left)
                }
            }
        }
    }

    private fun getProduct() = FavoriteProduct(
        id = args.id,
        title = binding.tvTitle.text.toString(),
        image = image,
        price = binding.tvPrice.text.toString().toInt()
    )
}
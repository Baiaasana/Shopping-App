package com.bendg.bg.presenter.ui.fragment.details

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bendg.bg.R
import com.bendg.bg.common.BaseFragment
import com.bendg.bg.data.local.model.FavoriteProduct
import com.bendg.bg.databinding.FragmentDetailsBinding
import com.bendg.bg.presenter.model.CartModel
import com.bendg.bg.extensions.cartList
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.snackbar.Snackbar
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

    private lateinit var cart: CartModel

    override fun listeners() {
        binding.btnAdd.setOnClickListener {
            if(!cartList.contains(cart)){
                cartList.add(cart)
                Snackbar.make(binding.root, getString(R.string.add_success), Snackbar.LENGTH_LONG).show()
            }else{
                Snackbar.make(binding.root, getString(R.string.already_added), Snackbar.LENGTH_LONG).show()
            }
        }

        binding.ivSetFavorite.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                saveProduct(getProduct())
                isSaved = !isSaved
            }
        }
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private suspend fun saveProduct(product: FavoriteProduct) {
        when (isSaved) {
            true -> {
                viewModel.removeProduct(product)
                binding.ivSetFavorite.setImageResource(R.drawable.ic_favorite_false)
            }
            false -> {
                viewModel.addProduct(product)
                binding.ivSetFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
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
                    val result = it.data
                    cart = CartModel(
                        id = result.id,
                        title = result.title,
                        price = result.price,
                        image = result.thumbnail
                    )
                    val slideList = ArrayList<SlideModel>()
                    it.data.images!!.forEach { imageUrl ->
                        val slideItem = SlideModel(imageUrl = imageUrl)
                        slideList.add(slideItem)
                    }
                    image = result.thumbnail ?: ""
                    price = result.price.toString().toInt()
                    binding.apply {
                        tvTitle.text = result.title.toString()
                        tvPrice.text = result.price.toString()
                        tvBrand.text = result.brand.toString()
                        tvDescription.text = result.description.toString()
                        tvRating.text = result.rating.toString()
                        binding.ivItem.setImageList(slideList)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoritesFlow.collect {
                val product = it.find { product ->
                    product.id == args.id
                }
                product?.let {
                    isSaved = true
                    binding.ivSetFavorite.setImageResource(R.drawable.ic_favorite_true)
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
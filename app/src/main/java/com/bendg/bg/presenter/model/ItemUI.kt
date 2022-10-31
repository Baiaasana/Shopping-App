package com.bendg.bg.presenter.model

import com.bendg.bg.data.remote.model.ItemModelDTO

data class ItemUI(
    val limit: Int?,
    val products: List<ItemModelDTO.Product>?,
    val skip: Int?,
    val total: Int?,
) {
    data class Product(
        val brand: String?,
        val category: String?,
        val description: String?,
        val discountPercentage: Double?,
        val id: Int?,
        val images: List<String>?,
        val price: Int?,
        val rating: Double?,
        val stock: Int?,
        val thumbnail: String?,
        val title: String?,
    )
}

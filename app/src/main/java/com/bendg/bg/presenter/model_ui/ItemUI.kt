package com.bendg.bg.presenter.model_ui

import com.bendg.bg.data.remote.model.ItemModelDTO
import com.bendg.bg.domain.model_domain.ItemModelDomain

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

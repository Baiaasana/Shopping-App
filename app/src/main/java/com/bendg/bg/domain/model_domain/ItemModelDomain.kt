package com.bendg.bg.domain.model_domain

import com.bendg.bg.data.remote.model.ItemModelDTO
import com.bendg.bg.presenter.model_ui.ItemUI

data class ItemModelDomain(
    val limit: Int?,
    val products: List<ItemModelDTO.Product>?,
    val skip: Int?,
    val total: Int?,
) {
    fun toPresenter(): ItemUI {
        return ItemUI(
            limit = limit,
            products = products,
            skip = skip,
            total = total
        )
    }

    data class ProductDomain(
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
    ) {
        fun toPresenterProduct(): ItemUI.Product {
            return ItemUI.Product(
                brand = brand,
                category = category,
                description = description,
                discountPercentage = discountPercentage,
                id = id,
                images = images,
                price = price,
                rating = rating,
                stock = stock,
                thumbnail = thumbnail,
                title = title
            )
        }
    }
}

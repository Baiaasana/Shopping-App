package com.bendg.bg.data.remote.model

import com.bendg.bg.domain.model.ProductModelDomain

data class ProductModelDto(
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
    fun toDomain(): ProductModelDomain {
        return ProductModelDomain(
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
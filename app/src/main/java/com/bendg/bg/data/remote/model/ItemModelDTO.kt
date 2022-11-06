package com.bendg.bg.data.remote.model

import com.bendg.bg.domain.model.ItemModelDomain

data class ItemModelDTO(
    val limit: Int?,
    val products: List<Product>?,
    val skip: Int?,
    val total: Int?,
) {
    fun toDomain(): ItemModelDomain {
        return ItemModelDomain(
            limit = limit,
            products = products,
            skip = skip,
            total = total
        )
    }

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
    ) {
        fun toDomainProduct(): ItemModelDomain.ProductDomain {
            return ItemModelDomain.ProductDomain(
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

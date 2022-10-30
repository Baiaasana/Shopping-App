package com.bendg.bg.data.remote.network

import com.bendg.bg.data.remote.model.CategoryModel
import com.bendg.bg.data.remote.model.ItemModelDTO
import com.bendg.bg.utility.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(Constants.PRODUCTS_END_POINT)
    suspend fun getProducts(): Response<ItemModelDTO>

    @GET(Constants.DETAILS_END_POINT)
    suspend fun getDetailedInfo(@Path("id") id: Int): Response<ItemModelDTO.Product>

    @GET(Constants.CATEGORIES_END_POINT)
    suspend fun getCategories(): Response<List<CategoryModel>>

    @GET(Constants.BY_CATEGORIES_END_POINT)
    suspend fun getProductsByCategory(@Path("category") category: String): Response<ItemModelDTO>

}
package com.bendg.bg.data.remote.network

import com.bendg.bg.common.Constants
import com.bendg.bg.data.remote.model.ItemModelDTO
import com.bendg.bg.data.remote.model.ProductModelDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.PRODUCTS_END_POINT)
    suspend fun getProducts(): Response<ItemModelDTO>

    @GET(Constants.DETAILS_END_POINT)
    suspend fun getProductById(@Path("id") id: Int): Response<ProductModelDto>

    @GET(Constants.BY_CATEGORIES_END_POINT)
    suspend fun getProductsByCategory(@Path("category") category: String): Response<ItemModelDTO>

    @GET(Constants.SEARCH_END_POINT)
    suspend fun getProductsBySearch(@Query("q") q: String): Response<ItemModelDTO>

}
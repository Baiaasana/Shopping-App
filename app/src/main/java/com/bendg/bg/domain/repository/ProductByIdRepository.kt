package com.bendg.bg.domain.repository

import com.bendg.bg.common.Resource
import com.bendg.bg.data.local.model.FavoriteProduct
import com.bendg.bg.domain.model_domain.ProductModelDomain
import kotlinx.coroutines.flow.Flow


interface ProductByIdRepository {

    suspend fun getDetailsByArgs(id: Int): Flow<Resource<ProductModelDomain>>

//    suspend fun getFavorites(): List<FavoriteProduct>
//
//    suspend fun addProduct(product: FavoriteProduct){}
//
//    suspend fun removeProduct(product: FavoriteProduct){}

}
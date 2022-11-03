package com.bendg.bg.data.repository

import com.bendg.bg.common.Resource
import com.bendg.bg.common.ResponseHandler
import com.bendg.bg.data.local.dao.ProductsDatabase
import com.bendg.bg.data.local.model.FavoriteProduct
import com.bendg.bg.data.remote.network.ApiService
import com.bendg.bg.domain.model_domain.ProductModelDomain
import com.bendg.bg.domain.repository.ProductByIdRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductByIdRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val responseHandler: ResponseHandler,
    private val database: ProductsDatabase,
) : ProductByIdRepository {

    override suspend fun getDetailsByArgs(id: Int): Flow<Resource<ProductModelDomain>> =
        flow {
            val result = responseHandler.safeApiCall { apiService.getProductById(id = id) }
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    emit(Resource.success(result.data!!.toDomain()))
                }
                Resource.Status.ERROR -> {
                    emit(Resource.error(result.message.toString()))
                }
                Resource.Status.LOADING -> {
                    Resource.loading(null)
                }
            }
        }

    override suspend fun getFavorites(): List<FavoriteProduct>{
        return database.getFavoriteProductsDao().getAllProducts()
    }

    override suspend fun addProduct(product: FavoriteProduct){
        database.getFavoriteProductsDao().addProduct(product = product)
    }

    override suspend fun removeProduct(product: FavoriteProduct){
        database.getFavoriteProductsDao().removeProduct(product = product)
    }
}
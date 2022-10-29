package com.bendg.bg.data.repository

import com.bendg.bg.common.ResponseHandler
import com.bendg.bg.data.remote.network.ApiService
import com.bendg.bg.domain.model_domain.ItemModelDomain
import com.bendg.bg.domain.repository.Repository
import com.bendg.bg.utility.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val responseHandler: ResponseHandler,
) : Repository {

    override suspend fun doNetworkCallProducts(): Flow<Resource<List<ItemModelDomain.ProductDomain>>> =
        flow {
            val result = responseHandler.safeApiCall { apiService.getProducts() }
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    emit(Resource.success(result.data!!.products!!.map {
                        it.toDomainProduct()
                    }))
                }
                Resource.Status.ERROR -> {
                    emit(Resource.error(result.message.toString()))
                }
                Resource.Status.LOADING -> {
                    Resource.loading(null)
                }
            }
        }
}
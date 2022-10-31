package com.bendg.bg.data.repository

import com.bendg.bg.common.ResponseHandler
import com.bendg.bg.data.remote.network.ApiService
import com.bendg.bg.domain.model.ItemModelDomain
import com.bendg.bg.domain.repository.ProductsByCategoryRepository
import com.bendg.bg.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductsByCategoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val responseHandler: ResponseHandler,
) : ProductsByCategoryRepository {

    override suspend fun getProductsByCategory(category: String): Flow<Resource<List<ItemModelDomain.ProductDomain>>> =
        flow {
            val result =
                responseHandler.safeApiCall { apiService.getProductsByCategory(category = category) }
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
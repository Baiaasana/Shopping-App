package com.bendg.bg.data.repository

import com.bendg.bg.common.ResponseHandler
import com.bendg.bg.data.remote.network.ApiService
import com.bendg.bg.domain.model_domain.ItemModelDomain
import com.bendg.bg.domain.repository.RepositoryWithArgs
import com.bendg.bg.utility.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailedRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val responseHandler: ResponseHandler,
) : RepositoryWithArgs {

    override suspend fun getDetailsByArgs(id: Int): Flow<Resource<ItemModelDomain.ProductDomain>> =
        flow {
            val result = responseHandler.safeApiCall { apiService.getDetailedInfo(id = id) }
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    emit(Resource.success(result.data!!.toDomainProduct()))
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
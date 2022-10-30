package com.bendg.bg.data.repository

import com.bendg.bg.common.ResponseHandler
import com.bendg.bg.data.remote.model.CategoryModel
import com.bendg.bg.data.remote.network.ApiService
import com.bendg.bg.domain.repository.CategoryRepository
import com.bendg.bg.utility.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val responseHandler: ResponseHandler,
) : CategoryRepository {

    override suspend fun getAllCategories(): Flow<Resource<List<CategoryModel>>> = flow {
        val result = responseHandler.safeApiCall { apiService.getCategories() }
        when (result.status) {
            Resource.Status.SUCCESS -> {
                emit(Resource.success(result.data!!))
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

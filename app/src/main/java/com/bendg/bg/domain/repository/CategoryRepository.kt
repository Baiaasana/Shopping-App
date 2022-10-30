package com.bendg.bg.domain.repository

import com.bendg.bg.data.remote.model.CategoryModel
import com.bendg.bg.utility.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CategoryRepository {

    suspend fun getAllCategories(): Flow<Resource<List<CategoryModel>>>

}
package com.bendg.bg.domain.repository

import com.bendg.bg.domain.model.ItemModelDomain
import com.bendg.bg.common.Resource
import kotlinx.coroutines.flow.Flow

interface ProductsByCategoryRepository {

    suspend fun getProductsByCategory(category: String): Flow<Resource<List<ItemModelDomain.ProductDomain>>>

}
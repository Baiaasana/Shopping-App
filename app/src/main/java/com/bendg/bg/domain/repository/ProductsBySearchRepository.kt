package com.bendg.bg.domain.repository

import com.bendg.bg.common.Resource
import com.bendg.bg.domain.model.ItemModelDomain
import kotlinx.coroutines.flow.Flow

interface ProductsBySearchRepository {

    suspend fun getProductsBySearch(searchWord: String): Flow<Resource<List<ItemModelDomain.ProductDomain>>>

}
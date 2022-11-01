package com.bendg.bg.domain.repository

import com.bendg.bg.domain.model_domain.ItemModelDomain
import com.bendg.bg.utility.Resource
import kotlinx.coroutines.flow.Flow

interface ProductsBySearchRepository {

    suspend fun getProductsBySearch(searchWord: String): Flow<Resource<List<ItemModelDomain.ProductDomain>>>

}
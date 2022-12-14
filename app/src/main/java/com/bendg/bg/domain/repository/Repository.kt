package com.bendg.bg.domain.repository

import com.bendg.bg.common.Resource
import com.bendg.bg.domain.model.ItemModelDomain
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun doNetworkCallProducts(): Flow<Resource<List<ItemModelDomain.ProductDomain>>>

}
package com.bendg.bg.domain.repository

import com.bendg.bg.data.remote.model.ItemModelDTO
import com.bendg.bg.domain.model_domain.ItemModelDomain
import com.bendg.bg.utility.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun doNetworkCallProducts(): Flow<Resource<List<ItemModelDomain.ProductDomain>>>

}
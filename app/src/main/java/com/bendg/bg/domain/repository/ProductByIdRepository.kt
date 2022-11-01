package com.bendg.bg.domain.repository

import com.bendg.bg.common.Resource
import com.bendg.bg.domain.model_domain.ProductModelDomain
import kotlinx.coroutines.flow.Flow


interface ProductByIdRepository {

    suspend fun getDetailsByArgs(id: Int): Flow<Resource<ProductModelDomain>>

}
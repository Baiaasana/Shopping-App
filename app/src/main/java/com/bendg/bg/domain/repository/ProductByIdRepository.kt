package com.bendg.bg.domain.repository

import com.bendg.bg.data.remote.model.ProductModelDto
import com.bendg.bg.domain.model_domain.ItemModelDomain
import com.bendg.bg.domain.model_domain.ProductModelDomain
import com.bendg.bg.utility.Resource
import kotlinx.coroutines.flow.Flow


interface ProductByIdRepository {

    suspend fun getDetailsByArgs(id: Int): Flow<Resource<ProductModelDomain>>

}
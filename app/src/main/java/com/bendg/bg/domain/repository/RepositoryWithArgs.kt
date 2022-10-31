package com.bendg.bg.domain.repository

import com.bendg.bg.domain.model.ItemModelDomain
import com.bendg.bg.common.Resource
import kotlinx.coroutines.flow.Flow


interface RepositoryWithArgs {

    suspend fun getDetailsByArgs(id: Int): Flow<Resource<ItemModelDomain.ProductDomain>>

}
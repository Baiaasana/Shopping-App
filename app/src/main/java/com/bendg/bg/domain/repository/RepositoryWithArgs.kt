package com.bendg.bg.domain.repository

import com.bendg.bg.domain.model_domain.ItemModelDomain
import com.bendg.bg.utility.Resource
import kotlinx.coroutines.flow.Flow


interface RepositoryWithArgs {

    suspend fun getDetailsByArgs(id: Int): Flow<Resource<ItemModelDomain.ProductDomain>>

}
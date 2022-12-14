package com.bendg.bg.domain.use_case

import com.bendg.bg.common.Resource
import com.bendg.bg.domain.model.ItemModelDomain
import com.bendg.bg.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsUseCase @Inject constructor(private val repository: Repository) {

    suspend fun invoke(): Flow<Resource<List<ItemModelDomain.ProductDomain>>>{
        return repository.doNetworkCallProducts()
    }
}
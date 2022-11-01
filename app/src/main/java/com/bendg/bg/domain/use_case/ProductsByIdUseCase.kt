package com.bendg.bg.domain.use_case

import com.bendg.bg.domain.model_domain.ProductModelDomain
import com.bendg.bg.domain.repository.ProductByIdRepository
import com.bendg.bg.utility.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsByIdUseCase @Inject constructor(
    private val repository: ProductByIdRepository
) {
    suspend fun invoke(id: Int): Flow<Resource<ProductModelDomain>>{
        return repository.getDetailsByArgs(id = id)
    }
}
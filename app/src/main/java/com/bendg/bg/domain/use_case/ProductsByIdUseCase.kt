package com.bendg.bg.domain.use_case

import com.bendg.bg.common.Resource
import com.bendg.bg.data.local.model.FavoriteProduct
import com.bendg.bg.data.repository.ProductByIdRepositoryImpl
import com.bendg.bg.domain.model_domain.ProductModelDomain
import com.bendg.bg.domain.repository.ProductByIdRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsByIdUseCase @Inject constructor(
    private val repository: ProductByIdRepository,
) {
    suspend fun invoke(id: Int): Flow<Resource<ProductModelDomain>>{
        return repository.getDetailsByArgs(id = id)
    }


}
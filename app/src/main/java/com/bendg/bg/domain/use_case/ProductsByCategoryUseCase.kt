package com.bendg.bg.domain.use_case

import com.bendg.bg.common.Resource
import com.bendg.bg.domain.model.ItemModelDomain
import com.bendg.bg.domain.repository.ProductsByCategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsByCategoryUseCase @Inject constructor(private val repository: ProductsByCategoryRepository) {

    suspend fun invoke(category: String): Flow<Resource<List<ItemModelDomain.ProductDomain>>>{
        return repository.getProductsByCategory(category = category)
    }
}
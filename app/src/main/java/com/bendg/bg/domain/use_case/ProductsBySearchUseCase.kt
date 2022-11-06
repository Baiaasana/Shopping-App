package com.bendg.bg.domain.use_case


import com.bendg.bg.common.Resource
import com.bendg.bg.domain.model.ItemModelDomain
import com.bendg.bg.domain.repository.ProductsBySearchRepository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsBySearchUseCase @Inject constructor(private val repository: ProductsBySearchRepository) {

    suspend fun invoke(searchWord: String): Flow<Resource<List<ItemModelDomain.ProductDomain>>> {
        return repository.getProductsBySearch(searchWord = searchWord)
    }
}
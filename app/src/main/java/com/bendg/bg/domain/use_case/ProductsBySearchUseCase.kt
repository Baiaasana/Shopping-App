package com.bendg.bg.domain.use_case

import com.bendg.bg.data.repository.ProductsBySearchRepositoryImpl
import com.bendg.bg.domain.model_domain.ItemModelDomain
import com.bendg.bg.domain.repository.ProductsByCategoryRepository
import com.bendg.bg.domain.repository.ProductsBySearchRepository
import com.bendg.bg.utility.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsBySearchUseCase @Inject constructor(private val repository: ProductsBySearchRepository) {

    suspend fun invoke(searchWord: String): Flow<Resource<List<ItemModelDomain.ProductDomain>>> {
        return repository.getProductsBySearch(searchWord = searchWord)
    }
}
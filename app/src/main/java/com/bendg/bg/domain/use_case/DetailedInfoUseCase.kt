package com.bendg.bg.domain.use_case

import com.bendg.bg.domain.model_domain.ItemModelDomain
import com.bendg.bg.domain.repository.RepositoryWithArgs
import com.bendg.bg.utility.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailedInfoUseCase @Inject constructor(
    private val repositoryWithArgs: RepositoryWithArgs
) {
    suspend fun invoke(id: Int): Flow<Resource<ItemModelDomain.ProductDomain>>{
        return repositoryWithArgs.getDetailsByArgs(id = id)
    }
}
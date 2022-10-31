package com.bendg.bg.domain.use_case

import com.bendg.bg.domain.model.ItemModelDomain
import com.bendg.bg.domain.repository.RepositoryWithArgs
import com.bendg.bg.common.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailedInfoUseCase @Inject constructor(
    private val repositoryWithArgs: RepositoryWithArgs
) {
    suspend fun invoke(id: Int): Flow<Resource<ItemModelDomain.ProductDomain>>{
        return repositoryWithArgs.getDetailsByArgs(id = id)
    }
}
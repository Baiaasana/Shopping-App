package com.bendg.bg.di

import com.bendg.bg.data.repository.CategoriesRepositoryImpl
import com.bendg.bg.data.repository.DetailedRepositoryImpl
import com.bendg.bg.data.repository.ProductsByCategoryRepositoryImpl
import com.bendg.bg.data.repository.ProductsRepositoryImpl
import com.bendg.bg.domain.repository.CategoryRepository
import com.bendg.bg.domain.repository.ProductsByCategoryRepository
import com.bendg.bg.domain.repository.Repository
import com.bendg.bg.domain.repository.RepositoryWithArgs
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productsRepositoryImpl: ProductsRepositoryImpl,
    ): Repository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        categoriesRepositoryImpl: CategoriesRepositoryImpl,
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindDetailedRepository(
        detailedRepositoryImpl: DetailedRepositoryImpl,
    ): RepositoryWithArgs

    @Binds
    @Singleton
    abstract fun bindProductsByCategoryRepository(
        productsByCategoryRepositoryImpl: ProductsByCategoryRepositoryImpl,
    ): ProductsByCategoryRepository
}
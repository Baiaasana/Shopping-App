package com.bendg.bg.di

import com.bendg.bg.data.repository.*
import com.bendg.bg.domain.repository.*
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
    abstract fun bindDetailedRepository(
        productByIdRepositoryImpl: ProductByIdRepositoryImpl
    ): ProductByIdRepository

    @Binds
    @Singleton
    abstract fun bindProductsByCategoryRepository(
        productsByCategoryRepositoryImpl: ProductsByCategoryRepositoryImpl,
    ): ProductsByCategoryRepository

    @Binds
    @Singleton
    abstract fun bindProductsBySearchRepository(
        productsBySearchRepositoryImpl: ProductsBySearchRepositoryImpl,
    ): ProductsBySearchRepository

    @Binds
    @Singleton
    abstract fun bindOrdersPerository(
        ordersRepositoryImpl: OrdersRepositoryImpl
    ): OrdersRepository

}
package com.akash.beautifulbhaluka.di

import com.akash.beautifulbhaluka.data.repository.BloodBankRepositoryImpl
import com.akash.beautifulbhaluka.data.repository.ImageRepositoryImpl
import com.akash.beautifulbhaluka.data.repository.NewsRepositoryImpl
import com.akash.beautifulbhaluka.domain.repository.BloodBankRepository
import com.akash.beautifulbhaluka.domain.repository.ImageRepository
import com.akash.beautifulbhaluka.domain.repository.NewsRepository
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
    abstract fun bindImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository

    @Binds
    @Singleton
    abstract fun bindBloodBankRepository(
        bloodBankRepositoryImpl: BloodBankRepositoryImpl
    ): BloodBankRepository

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository
}


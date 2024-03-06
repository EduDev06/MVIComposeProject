package com.example.mvicomposeproject.data.network.di

import com.example.mvicomposeproject.data.network.repository.NetworkDataSource
import com.example.mvicomposeproject.data.network.repository.NetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkDataSourceModule {

    @Binds
    abstract fun bindNetworkDataSource(impl: NetworkDataSourceImpl): NetworkDataSource
}
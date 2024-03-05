package com.example.mvicomposeproject.data.network.repository

import com.example.mvicomposeproject.data.di.IoDispatcher
import com.example.mvicomposeproject.data.network.api.GenderApi
import com.example.mvicomposeproject.data.network.model.GenderUserResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val api: GenderApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): NetworkDataSource {
    override suspend fun fetchNetworkGenderUser(name: String): GenderUserResponse {
        return withContext(dispatcher) {
            api.fetchGenderUser(name)
        }
    }
}
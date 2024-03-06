package com.example.mvicomposeproject.data.network.repository

import com.example.mvicomposeproject.data.network.model.GenderUserResponse

interface NetworkDataSource {

    suspend fun fetchNetworkGenderUser(name: String): GenderUserResponse
}
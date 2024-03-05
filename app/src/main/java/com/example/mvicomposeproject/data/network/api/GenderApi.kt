package com.example.mvicomposeproject.data.network.api

import com.example.mvicomposeproject.data.network.model.GenderUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GenderApi {

    @GET("/")
    suspend fun fetchGenderUser(@Query("name") name: String): GenderUserResponse
}
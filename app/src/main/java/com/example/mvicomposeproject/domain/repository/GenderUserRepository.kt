package com.example.mvicomposeproject.domain.repository

import com.example.mvicomposeproject.domain.model.GenderUser
import kotlinx.coroutines.flow.Flow

interface GenderUserRepository {
    fun getGenderUser(name: String): Flow<GenderUser>
}
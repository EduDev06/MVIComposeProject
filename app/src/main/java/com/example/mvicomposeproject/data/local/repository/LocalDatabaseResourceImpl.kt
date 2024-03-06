package com.example.mvicomposeproject.data.local.repository

import com.example.mvicomposeproject.data.di.IoDispatcher
import com.example.mvicomposeproject.data.local.dao.GenderUserDao
import com.example.mvicomposeproject.data.local.entity.GenderUserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDatabaseResourceImpl @Inject constructor(
    private val dao: GenderUserDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): LocalDatabaseResource {
    override fun getGenderUserEntity(name: String): Flow<GenderUserEntity> {
        return dao.getGenderUser(name).flowOn(dispatcher)
    }

    override suspend fun saveGenderUserEntity(genderUserEntity: GenderUserEntity) {
        withContext(dispatcher) {
            dao.saveGenderUser(genderUserEntity)
        }
    }
}
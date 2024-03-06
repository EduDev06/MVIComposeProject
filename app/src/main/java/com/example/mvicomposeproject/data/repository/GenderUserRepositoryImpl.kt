package com.example.mvicomposeproject.data.repository

import com.example.mvicomposeproject.data.local.entity.asExternalModel
import com.example.mvicomposeproject.data.local.repository.LocalDatabaseResource
import com.example.mvicomposeproject.data.network.model.asEntity
import com.example.mvicomposeproject.data.network.repository.NetworkDataSource
import com.example.mvicomposeproject.domain.model.GenderUser
import com.example.mvicomposeproject.domain.repository.GenderUserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class GenderUserRepositoryImpl @Inject constructor(
    private val localDatabaseResource: LocalDatabaseResource,
    private val networkDataSource: NetworkDataSource
): GenderUserRepository {
    override fun getGenderUser(name: String): Flow<GenderUser> {
        return localDatabaseResource
            .getGenderUserEntity(name)
            .map { genderUserEntity ->
                delay(200)
                genderUserEntity.asExternalModel()
            }
            .retryWhen { cause, attempt ->
                if (cause is NullPointerException && attempt < 1) {
                    fetchGenderUser(name)
                    true
                } else {
                    false
                }
            }
            .take(1)
    }

    private suspend fun fetchGenderUser(name: String): GenderUser {
        return networkDataSource.fetchNetworkGenderUser(name)
            .asEntity()
            .also { localDatabaseResource.saveGenderUserEntity(it) }
            .asExternalModel()
    }
}
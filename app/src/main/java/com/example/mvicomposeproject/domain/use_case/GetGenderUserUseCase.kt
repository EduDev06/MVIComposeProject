package com.example.mvicomposeproject.domain.use_case

import com.example.mvicomposeproject.domain.model.GenderUser
import com.example.mvicomposeproject.domain.repository.GenderUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetGenderUserUseCase @Inject constructor(
    private val repository: GenderUserRepository
) {
    operator fun invoke(name: String): Flow<Result<GenderUser>> {
        return repository
            .getGenderUser(name)
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }
}
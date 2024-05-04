package com.example.curatorrtumirea.domain.usecase

import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.repository.AuthRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IsSessionValidUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke() = flow {
        try {
            emit(Resource.Loading)
            val isSessionValid = authRepository.isSessionValid()
            emit(Resource.Success(isSessionValid))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}
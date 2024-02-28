package com.example.curatorrtumirea.domain.usecase

import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConfirmEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(code: String) = flow {
        try {
            emit(Resource.Loading)
            val result = authRepository.confirmEmail(code)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}
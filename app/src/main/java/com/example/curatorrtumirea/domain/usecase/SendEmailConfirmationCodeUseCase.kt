package com.example.curatorrtumirea.domain.usecase

import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendEmailConfirmationCodeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(email: String) = flow {
        try {
            emit(Resource.Loading)
            authRepository.sendConfirmationCode(email)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}
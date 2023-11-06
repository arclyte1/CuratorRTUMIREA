package com.example.curatorrtumirea.domain.usecase

import com.example.curatorrtumirea.common.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendEmailVerificationCodeUseCase @Inject constructor() {

    operator fun invoke(email: String) = flow {
        emit(Resource.Loading)
        delay(3000L)
        emit(Resource.Success(""))
    }
}
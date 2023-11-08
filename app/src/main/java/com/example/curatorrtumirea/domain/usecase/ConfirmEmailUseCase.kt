package com.example.curatorrtumirea.domain.usecase

import com.example.curatorrtumirea.common.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConfirmEmailUseCase @Inject constructor() {

    operator fun invoke(code: String) = flow {
        emit(Resource.Loading)
        delay(3000L)
        emit(Resource.Success(true))
    }
}
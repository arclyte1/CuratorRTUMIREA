package com.example.curatorrtumirea.domain.usecase

import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.repository.RequestRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateRequestUseCase @Inject constructor(
    private val requestRepository: RequestRepository
) {

    operator fun invoke(title: String, description: String) = flow {
        try {
            emit(Resource.Loading)
            val res = requestRepository.createRequest(title, description)
            emit(Resource.Success(res))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}
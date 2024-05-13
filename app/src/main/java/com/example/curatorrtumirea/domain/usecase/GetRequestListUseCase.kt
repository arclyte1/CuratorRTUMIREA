package com.example.curatorrtumirea.domain.usecase

import android.util.Log
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.repository.RequestRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRequestListUseCase @Inject constructor(
    private val requestRepository: RequestRepository
) {

    operator fun invoke() = flow {
        try {
            emit(Resource.Loading)
            val requests = requestRepository.getRequestList()
            Log.d("REQUESTS", requests.toString())
            emit(Resource.Success(requests))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}
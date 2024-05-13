package com.example.curatorrtumirea.data.repository

import com.example.curatorrtumirea.data.remote.api.MainApi
import com.example.curatorrtumirea.data.remote.request.CreateRequestRequest
import com.example.curatorrtumirea.domain.model.Request
import com.example.curatorrtumirea.domain.model.RequestStatus
import com.example.curatorrtumirea.domain.repository.RequestRepository
import javax.inject.Inject

class RequestRepositoryImpl @Inject constructor(
    private val api: MainApi
) : RequestRepository {

    override suspend fun getRequestList(): List<Request> {
        return api.getRequestList().map { it.toRequest() }.sortedByDescending { it.id }
    }

    override suspend fun createRequest(title: String, description: String): Request {
        return api.createRequest(CreateRequestRequest(
            title = title,
            description = description,
            status = RequestStatus.OPEN
        )).toRequest()
    }
}
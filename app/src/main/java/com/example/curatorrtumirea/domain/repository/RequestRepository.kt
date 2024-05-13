package com.example.curatorrtumirea.domain.repository

import com.example.curatorrtumirea.domain.model.Request

interface RequestRepository {

    suspend fun getRequestList(): List<Request>

    suspend fun createRequest(title: String, description: String): Request
}
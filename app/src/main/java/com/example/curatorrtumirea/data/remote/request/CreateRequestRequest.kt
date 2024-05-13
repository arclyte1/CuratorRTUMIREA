package com.example.curatorrtumirea.data.remote.request

import com.example.curatorrtumirea.domain.model.RequestStatus
import kotlinx.serialization.Serializable

@Serializable
data class CreateRequestRequest(
    val title: String,
    val description: String,
    val status: RequestStatus,
)

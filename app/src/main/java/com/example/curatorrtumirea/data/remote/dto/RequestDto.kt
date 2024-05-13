package com.example.curatorrtumirea.data.remote.dto

import com.example.curatorrtumirea.domain.model.Request
import com.example.curatorrtumirea.domain.model.RequestStatus
import kotlinx.serialization.Serializable

@Serializable
data class RequestDto(
    val id: Long,
    val title: String,
    val description: String,
    val status: String,
) {

    fun toRequest(): Request {
        return Request(
            id = id,
            title = title,
            description = description,
            status = RequestStatus.valueOf(status)
        )
    }
}
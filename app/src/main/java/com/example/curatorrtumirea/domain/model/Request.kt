package com.example.curatorrtumirea.domain.model

data class Request(
    val id: Long,
    val title: String,
    val description: String,
    val status: RequestStatus,
)

enum class RequestStatus {
    OPEN,
    REJECTED,
    RESOLVED
}
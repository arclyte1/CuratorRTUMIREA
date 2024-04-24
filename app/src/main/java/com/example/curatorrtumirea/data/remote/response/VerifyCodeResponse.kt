package com.example.curatorrtumirea.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class VerifyCodeResponse(
    val id: Long,
    val email: String,
    val access: String,
    val refresh: String
)

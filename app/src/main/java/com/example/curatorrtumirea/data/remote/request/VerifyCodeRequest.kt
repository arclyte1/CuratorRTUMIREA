package com.example.curatorrtumirea.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class VerifyCodeRequest(
    val email: String,
    val code: String
)

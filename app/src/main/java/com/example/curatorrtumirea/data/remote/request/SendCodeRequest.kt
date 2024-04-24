package com.example.curatorrtumirea.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class SendCodeRequest(
    val email: String
)

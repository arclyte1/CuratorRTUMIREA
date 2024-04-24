package com.example.curatorrtumirea.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val access: String
)

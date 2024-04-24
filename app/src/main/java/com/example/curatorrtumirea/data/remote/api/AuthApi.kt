package com.example.curatorrtumirea.data.remote.api

import com.example.curatorrtumirea.data.remote.request.RefreshTokenRequest
import com.example.curatorrtumirea.data.remote.request.SendCodeRequest
import com.example.curatorrtumirea.data.remote.request.VerifyCodeRequest
import com.example.curatorrtumirea.data.remote.response.RefreshTokenResponse
import com.example.curatorrtumirea.data.remote.response.VerifyCodeResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("refresh-token")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): RefreshTokenResponse

    @POST("send-code")
    suspend fun sendCode(
        @Body request: SendCodeRequest
    )

    @POST("verify-code")
    suspend fun verifyCode(
        @Body request: VerifyCodeRequest
    ): VerifyCodeResponse
}
package com.example.curatorrtumirea.domain.repository

interface AuthRepository {

    suspend fun sendConfirmationCode(email: String)

    suspend fun confirmEmail(code: String): Boolean

    suspend fun logout()

    suspend fun isSessionValid(): Boolean

    suspend fun getUserEmail(): String?
}
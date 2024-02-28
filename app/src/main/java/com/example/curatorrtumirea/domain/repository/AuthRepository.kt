package com.example.curatorrtumirea.domain.repository

interface AuthRepository {

    suspend fun sendConfirmationCode(email: String): Boolean

    suspend fun confirmEmail(code: String): Boolean
}
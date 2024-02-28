package com.example.curatorrtumirea.data.repository

import android.util.Log
import com.example.curatorrtumirea.domain.repository.AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {

    private var email: String = ""

    override suspend fun sendConfirmationCode(email: String): Boolean {
        Log.d(this.javaClass.simpleName, "sendConfirmationCode")
        delay(3000)
        this.email = email
        return true
    }

    override suspend fun confirmEmail(code: String): Boolean {
        Log.d(this.javaClass.simpleName, "confirmEmail")
        delay(3000)
        return true
    }
}
package com.example.curatorrtumirea.data.repository

import android.content.SharedPreferences
import com.example.curatorrtumirea.common.Constants
import com.example.curatorrtumirea.data.remote.api.AuthApi
import com.example.curatorrtumirea.data.local.SessionManager
import com.example.curatorrtumirea.data.remote.request.SendCodeRequest
import com.example.curatorrtumirea.data.remote.request.VerifyCodeRequest
import com.example.curatorrtumirea.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val sessionManager: SessionManager,
    private val sharedPrefs: SharedPreferences
) : AuthRepository {

    private var email: String? = sharedPrefs.getString(Constants.SharedPrefs.EMAIL, null)

    override suspend fun sendConfirmationCode(email: String) {
        api.sendCode(SendCodeRequest(email))
        this.email = email
    }

    override suspend fun confirmEmail(code: String): Boolean {
        return try {
            val response = api.verifyCode(VerifyCodeRequest(email!!, code))
            sessionManager.saveAccessToken(response.access)
            sessionManager.saveRefreshToken(response.refresh)
            sharedPrefs.edit().putString(Constants.SharedPrefs.EMAIL, response.email).apply()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun logout() {
        sessionManager.clearTokens()
        // TODO: api call
    }
}
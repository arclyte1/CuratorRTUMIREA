package com.example.curatorrtumirea.data.local

import android.content.SharedPreferences
import com.auth0.android.jwt.JWT
import com.example.curatorrtumirea.common.Constants
import com.example.curatorrtumirea.data.remote.api.AuthApi
import com.example.curatorrtumirea.data.remote.request.RefreshTokenRequest
import com.example.curatorrtumirea.di.EncryptedSharedPrefs
import javax.inject.Inject


class SessionManager @Inject constructor(
    @EncryptedSharedPrefs private val sharedPrefs: SharedPreferences,
    private val api: AuthApi
) {

    private var accessToken: JWT? =
        sharedPrefs.getString(Constants.SharedPrefs.ACCESS_TOKEN, null)?.let { JWT(it) }
    private var refreshToken: JWT? =
        sharedPrefs.getString(Constants.SharedPrefs.REFRESH_TOKEN, null)?.let { JWT(it) }

    fun saveAccessToken(access: String) {
        sharedPrefs.edit().putString(
            Constants.SharedPrefs.ACCESS_TOKEN, access
        ).apply()
        accessToken = JWT(access)
    }

    fun saveRefreshToken(refresh: String) {
        sharedPrefs.edit().putString(
            Constants.SharedPrefs.REFRESH_TOKEN, refresh
        ).apply()
        refreshToken = JWT(refresh)
    }

    /**
     * @return true if refreshing Job is launched or access token is valid
     * else false if refresh token expired
     */
    private suspend fun refreshTokensIfNeed(): Boolean {
        if (accessToken?.isExpired(10) == false)
            return true
        return if (
            accessToken?.isExpired(10) != false &&
            refreshToken?.isExpired(10) == false
        ) {
            val response = api.refreshToken(RefreshTokenRequest(refreshToken.toString()))
            saveAccessToken(response.access)
            true
        } else
            false
    }

    suspend fun getAccessToken(): GetTokenResult {
        return try {
            val refreshStatus = refreshTokensIfNeed()
            if (refreshStatus) {
                GetTokenResult.Success(accessToken.toString())
            } else {
                GetTokenResult.RefreshExpired
            }
        } catch (e: Exception) {
            GetTokenResult.Error(e)
        }
    }

    fun clearTokens() {
        sharedPrefs.edit()
            .putString(Constants.SharedPrefs.ACCESS_TOKEN, null)
            .putString(Constants.SharedPrefs.REFRESH_TOKEN, null)
            .apply()
        accessToken = null
        refreshToken = null
    }

    sealed class GetTokenResult {
        data class Success(val token: String) : GetTokenResult()
        data object RefreshExpired : GetTokenResult()
        data class Error(val e: Exception) : GetTokenResult()
    }
}
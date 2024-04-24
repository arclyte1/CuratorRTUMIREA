package com.example.curatorrtumirea.data.remote.interceptor

import android.content.Context
import android.widget.Toast
import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.data.local.SessionManager
import com.example.curatorrtumirea.presentation.navigation.AppNavigator
import com.example.curatorrtumirea.presentation.navigation.Destination
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager,
    private val appNavigator: AppNavigator,
    @ApplicationContext private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessTokenResult = runBlocking { sessionManager.getAccessToken() }

        return if (accessTokenResult is SessionManager.GetTokenResult.Success) {
            val authorizedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer ${accessTokenResult.token}")
                .build()
            chain.proceed(authorizedRequest)
        } else {
            appNavigator.tryNavigateTo(Destination.LoginScreen(), clearBackStack = true)
            Toast.makeText(context, context.getString(R.string.session_expired), Toast.LENGTH_LONG)
                .show()
            chain.proceed(originalRequest)
        }
    }
}
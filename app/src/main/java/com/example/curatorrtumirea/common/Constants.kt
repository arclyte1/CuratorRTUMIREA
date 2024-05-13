package com.example.curatorrtumirea.common

import com.example.curatorrtumirea.BuildConfig

object Constants {

    const val BASE_URL = BuildConfig.BASE_URL

    object SharedPrefs {
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val EMAIL = "email"
    }
}
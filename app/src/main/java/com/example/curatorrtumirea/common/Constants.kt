package com.example.curatorrtumirea.common

import com.example.curatorrtumirea.BuildConfig

object Constants {

    const val BASE_URL = "http://192.168.0.105:8000/api/v1/"//BuildConfig.BASE_URL

    object SharedPrefs {
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val EMAIL = "email"
    }
}
package com.example.curatorrtumirea.common

import java.lang.Exception

sealed class Result<in T> {
    data class Success<T> (val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Any>()
}
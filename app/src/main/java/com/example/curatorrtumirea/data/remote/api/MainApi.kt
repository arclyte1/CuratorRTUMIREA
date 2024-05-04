package com.example.curatorrtumirea.data.remote.api

import com.example.curatorrtumirea.data.remote.response.EventListResponse
import com.example.curatorrtumirea.data.remote.response.GroupListResponse
import retrofit2.http.GET

interface MainApi {

    @GET("events")
    suspend fun getEventList(): EventListResponse

    @GET("groups")
    suspend fun getGroupList(): GroupListResponse
}
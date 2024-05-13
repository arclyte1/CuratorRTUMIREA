package com.example.curatorrtumirea.data.remote.api

import com.example.curatorrtumirea.data.remote.dto.EventDto
import com.example.curatorrtumirea.data.remote.dto.GroupDto
import com.example.curatorrtumirea.data.remote.dto.RequestDto
import com.example.curatorrtumirea.data.remote.request.CreateRequestRequest
import com.example.curatorrtumirea.data.remote.request.UpsertEventRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MainApi {

    @GET("events")
    suspend fun getEventList(): List<EventDto>

    @POST("events")
    suspend fun createEvent(@Body request: UpsertEventRequest): EventDto

    @DELETE("events/{id}")
    suspend fun deleteEvent(@Path("id") id: Long)

    @GET("groups")
    suspend fun getGroupList(): List<GroupDto>

    @GET("requests")
    suspend fun getRequestList(): List<RequestDto>

    @PUT("events/{id}")
    suspend fun updateEvent(
        @Path("id") id: Long,
        @Body request: UpsertEventRequest
    ): EventDto

    @POST("requests")
    suspend fun createRequest(
        @Body request: CreateRequestRequest
    ): RequestDto
}
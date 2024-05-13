package com.example.curatorrtumirea.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpsertEventRequest(
    val title: String,
    val type: String,
    val date: String? = null,
    @SerialName("start_time")
    val startTime: String? = null,
    @SerialName("end_time")
    val endTime: String? = null,
    val location: String? = null,
    @SerialName("cloud_url")
    val cloudUrl: String? = null,
    val groups: List<Long>,
    @SerialName("present_students")
    val presentStudents: List<Long> = emptyList(),
)

package com.example.curatorrtumirea.data.remote.dto

import com.example.curatorrtumirea.domain.model.Event
import com.example.curatorrtumirea.domain.model.EventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime

@Serializable
data class EventDto(
    val id: Long,
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
    val presentStudents: List<Long>
) {

    fun toEvent(): Event {
        return Event(
            id = this.id,
            title = this.title,
            type = EventType.valueOf(this.type),
            date = this.date?.let { LocalDate.parse(it) },
            startTime = this.startTime?.let { LocalTime.parse(it) },
            endTime = this.endTime?.let { LocalTime.parse(it) },
            location = this.location,
            cloudUrl = this.cloudUrl,
            groupIds = this.groups,
            presentStudents = this.presentStudents
        )
    }
}
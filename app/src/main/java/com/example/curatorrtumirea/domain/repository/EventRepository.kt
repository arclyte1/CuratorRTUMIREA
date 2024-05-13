package com.example.curatorrtumirea.domain.repository

import com.example.curatorrtumirea.domain.model.Event
import com.example.curatorrtumirea.domain.model.EventType
import java.time.LocalDate
import java.time.LocalTime

interface EventRepository {

    suspend fun getEventList(forceRefresh: Boolean): List<Event>

    suspend fun getEventDetails(eventId: Long): Event?

    suspend fun createEvent(
        title: String,
        type: EventType,
        groupIds: List<Long>,
        date: LocalDate? = null,
        startTime: LocalTime? = null,
        endTime: LocalTime? = null,
        location: String? = null,
        cloudUrl: String? = null
    ): Event

    suspend fun deleteEvent(id: Long)

    suspend fun setAttendance(eventId: Long, studentId: Long, isPresent: Boolean): Event?
}
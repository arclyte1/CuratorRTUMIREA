package com.example.curatorrtumirea.data.repository

import android.util.Log
import com.example.curatorrtumirea.data.remote.api.MainApi
import com.example.curatorrtumirea.data.remote.dto.EventDto
import com.example.curatorrtumirea.data.remote.request.UpsertEventRequest
import com.example.curatorrtumirea.domain.model.Event
import com.example.curatorrtumirea.domain.model.EventType
import com.example.curatorrtumirea.domain.repository.EventRepository
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val api: MainApi
) : EventRepository {

    private var events: List<Event>? = null

    private suspend fun getRemoteEventList(): List<Event> {
        val response = api.getEventList().sortedBy { -it.id }
        Log.d(this.javaClass.simpleName, response.joinToString())
        return response.map(EventDto::toEvent)
    }

    private fun upsertEvent(event: Event) {
        events = events?.filterNot { it.id == event.id }?.plus(event)?.sortedBy { -it.id }
    }

    override suspend fun getEventList(forceRefresh: Boolean): List<Event> {
        if (forceRefresh || events == null) {
            events = getRemoteEventList()
        }
        return events ?: emptyList()
    }

    override suspend fun getEventDetails(eventId: Long): Event? {
        if (events == null || events!!.all { it.id != eventId }) {
            events = getRemoteEventList()
        }
        return events?.find { it.id == eventId }
    }

    override suspend fun createEvent(
        title: String,
        type: EventType,
        groupIds: List<Long>,
        date: LocalDate?,
        startTime: LocalTime?,
        endTime: LocalTime?,
        location: String?,
        cloudUrl: String?
    ): Event {
        Log.d(this.javaClass.simpleName, type.toString())
        val r = UpsertEventRequest(
            title = title,
            type = type.name,
            groups = groupIds,
            date = date?.format(DateTimeFormatter.ISO_DATE),
            startTime = startTime?.format(DateTimeFormatter.ISO_TIME),
            endTime = endTime?.format(DateTimeFormatter.ISO_TIME),
            location = location,
            cloudUrl = cloudUrl
        )
        Log.d(this.javaClass.simpleName, r.toString())
        val event = api.createEvent(
            r
        ).toEvent()
        upsertEvent(event)
        return event
    }

    override suspend fun deleteEvent(id: Long) {
        api.deleteEvent(id)
        events = events?.filterNot { it.id == id }
    }

    override suspend fun setAttendance(eventId: Long, studentId: Long, isPresent: Boolean): Event? {
        val event = getEventDetails(eventId) ?: return null
        var presentStudents = event.presentStudents - studentId
        if (isPresent) {
            presentStudents += studentId
        }
        Log.d("PRESENT STUDENTS", presentStudents.toString())
        val newEvent = api.updateEvent(
            eventId,
            UpsertEventRequest(
                title = event.title,
                type = event.type.name,
                groups = event.groupIds,
                date = event.date?.format(DateTimeFormatter.ISO_DATE),
                startTime = event.startTime?.format(DateTimeFormatter.ISO_TIME),
                endTime = event.endTime?.format(DateTimeFormatter.ISO_TIME),
                location = event.location,
                cloudUrl = event.cloudUrl,
                presentStudents = presentStudents
            )
        ).toEvent()
        Log.d("NEW EVENT", newEvent.toString())
        upsertEvent(newEvent)
        return newEvent
    }
}
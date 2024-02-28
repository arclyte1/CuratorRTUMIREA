package com.example.curatorrtumirea.data.repository

import android.util.Log
import com.example.curatorrtumirea.domain.model.Event
import com.example.curatorrtumirea.domain.model.EventGroup
import com.example.curatorrtumirea.domain.model.EventType
import com.example.curatorrtumirea.domain.repository.EventRepository
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date

class EventRepositoryImpl : EventRepository {

    private var events: List<Event>? = null

    private suspend fun getRemoteEventList(): List<Event> {
        Log.d(this.javaClass.simpleName, "getRemoteEventList")
        delay(1000)
        return listOf(
            Event(
                id = 1L,
                title = "II Международная научно-практическая конференция цифровые международные отношения 2023",
                type = EventType.OFFSITE,
                date = LocalDate.now()
            ),
            Event(
                id = 0L,
                title = "II Международная научно-практическая конференция цифровые международные отношения 2023",
                type = EventType.OFFSITE,
                date = LocalDate.now(),
                startTime = LocalTime.of(10, 10),
                endTime = LocalTime.of(12, 40),
                location = "A-410",
                cloudUrl = "https://google.com/shdufsdkjfiewfmdskocnwe9ifjsdkdokf",
                studentsCount = 30,
                studentsPresent = 10,
                groupIds = listOf(
                    6L,
                    7L,
                    8L
                )
            ),
            Event(
                id = 2L,
                title = "Круглый стол \"Перспективы развития цифровых технологий: опыт в России и за рубежом\"",
                type = EventType.FACE_TO_FACE,
            ),
            Event(
                id = 3L,
                title = "Конференция Flutter meetup",
                type = EventType.CAREER_GUIDANCE,
            ),
            Event(
                id = 4L,
                title = "Другое мероприятие",
                type = EventType.OTHER,
            ),
            Event(
                id = 10L,
                title = "II Международная научно-практическая конференция цифровые международные отношения 2023",
                type = EventType.OFFSITE,
                date = LocalDate.now()
            ),
            Event(
                id = 2L,
                title = "Круглый стол \"Перспективы развития цифровых технологий: опыт в России и за рубежом\"",
                type = EventType.FACE_TO_FACE,
            ),
            Event(
                id = 3L,
                title = "Конференция Flutter meetup",
                type = EventType.CAREER_GUIDANCE,
            ),
            Event(
                id = 4L,
                title = "Другое мероприятие",
                type = EventType.OTHER,
            )
        )
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
}
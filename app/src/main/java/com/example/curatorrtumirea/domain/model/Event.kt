package com.example.curatorrtumirea.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class Event(
    val id: Long,
    val title: String,
    val type: EventType,
    val date: LocalDate? = null,
    val startTime: LocalTime? = null,
    val endTime: LocalTime? = null,
    val location: String? = null,
    val cloudUrl: String? = null,
    val studentsCount: Int? = null,
    val studentsPresent: Int? = null,
    val groupIds: List<Long> = emptyList()
)

enum class EventType {
    FACE_TO_FACE, OFFSITE, CAREER_GUIDANCE, OTHER
}

data class EventGroup(
    val id: Long,
    val title: String,
)
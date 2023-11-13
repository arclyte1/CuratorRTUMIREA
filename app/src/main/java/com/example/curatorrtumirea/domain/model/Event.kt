package com.example.curatorrtumirea.domain.model

import java.util.Date

data class Event(
    val id: Long,
    val title: String,
    val type: EventType,
    val date: Date? = null,
    val startTime: Date? = null,
    val endTime: Date? = null,
    val location: String? = null,
    val cloudUrl: String? = null,
)

enum class EventType {
    FACE_TO_FACE, OFFSITE, CAREER_GUIDANCE, OTHER
}

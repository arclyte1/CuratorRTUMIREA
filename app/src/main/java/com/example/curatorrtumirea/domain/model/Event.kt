package com.example.curatorrtumirea.domain.model

import java.util.Date

data class Event(
    val title: String,
    val type: EventType,
    val date: Date?,
    val startTime: Date?,
    val endTime: Date?,
    val location: String?,
    val cloudUrl: String?,
)

enum class EventType {
    FACE_TO_FACE, OFFSITE, CAREER_GUIDANCE, OTHER
}

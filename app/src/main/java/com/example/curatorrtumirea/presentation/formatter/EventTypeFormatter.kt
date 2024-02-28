package com.example.curatorrtumirea.presentation.formatter

import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.domain.model.EventType

object EventTypeFormatter {

    fun getEventTypeStringResourceId(eventType: EventType): Int? {
        return when(eventType) {
            EventType.FACE_TO_FACE -> R.string.face_to_face_event_type
            EventType.OFFSITE -> R.string.offsite_event_type
            EventType.CAREER_GUIDANCE -> R.string.career_guidance_event_type
            EventType.OTHER -> null
        }
    }
}
package com.example.curatorrtumirea.presentation.shared.event_card

import com.example.curatorrtumirea.R
import com.example.curatorrtumirea.domain.model.Event
import com.example.curatorrtumirea.domain.model.EventType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class EventCardModel(
    val title: String,
    val typeResourceId: Int?,
    val date: String?,
) {

    companion object {
        private fun getEventTypeStringResourceId(eventType: EventType): Int? {
            return when(eventType) {
                EventType.FACE_TO_FACE -> R.string.face_to_face_event_type
                EventType.OFFSITE -> R.string.offsite_event_type
                EventType.CAREER_GUIDANCE -> R.string.career_guidance_event_type
                EventType.OTHER -> null
            }
        }

        private fun formatDate(date: Date?): String? {
            val formatter = SimpleDateFormat("dd.MM", Locale.getDefault())
            return date?.let { formatter.format(it) }
        }

        fun fromEvent(event: Event): EventCardModel {
            return EventCardModel(
                title = event.title,
                typeResourceId = getEventTypeStringResourceId(event.type),
                date = formatDate(event.date)
            )
        }
    }
}

package com.example.curatorrtumirea.data.repository

import android.util.Log
import com.example.curatorrtumirea.data.remote.api.MainApi
import com.example.curatorrtumirea.data.remote.dto.EventDto
import com.example.curatorrtumirea.domain.model.Event
import com.example.curatorrtumirea.domain.repository.EventRepository
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val api: MainApi
) : EventRepository {

    private var events: List<Event>? = null

    private suspend fun getRemoteEventList(): List<Event> {
        val response = api.getEventList()
        Log.d(this.javaClass.simpleName, response.joinToString())
        return response.map(EventDto::toEvent)
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
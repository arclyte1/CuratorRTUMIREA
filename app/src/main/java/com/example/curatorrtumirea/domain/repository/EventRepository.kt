package com.example.curatorrtumirea.domain.repository

import com.example.curatorrtumirea.domain.model.Event

interface EventRepository {

    suspend fun getEventList(forceRefresh: Boolean): List<Event>

    suspend fun getEventDetails(eventId: Long): Event?
}
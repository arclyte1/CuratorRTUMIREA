package com.example.curatorrtumirea.domain.usecase

import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.model.Event
import com.example.curatorrtumirea.domain.model.EventType
import com.example.curatorrtumirea.domain.repository.EventRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class GetEventListUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {

    operator fun invoke(forceRefresh: Boolean) = flow {
        try {
            emit(Resource.Loading)
            val events = eventRepository.getEventList(forceRefresh)
            emit(Resource.Success(events))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}
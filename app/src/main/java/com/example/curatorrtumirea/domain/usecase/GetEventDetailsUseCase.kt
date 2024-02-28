package com.example.curatorrtumirea.domain.usecase

import android.util.Log
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.repository.EventRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetEventDetailsUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {

    operator fun invoke(eventId: Long) = flow {
        try {
            emit(Resource.Loading)
            val event = eventRepository.getEventDetails(eventId)
            Log.d("GET EVENT DETAILS", event.toString())
            emit(Resource.Success(event))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}
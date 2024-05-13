package com.example.curatorrtumirea.domain.usecase

import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.model.EventType
import com.example.curatorrtumirea.domain.repository.EventRepository
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class CreateEventUseCase @Inject constructor(
    private val repository: EventRepository
) {

    operator fun invoke(
        title: String,
        type: EventType,
        groupIds: List<Long>,
        date: LocalDate? = null,
        startTime: LocalTime? = null,
        endTime: LocalTime? = null,
        location: String? = null,
        cloudUrl: String? = null
    ) = flow {
        try {
            emit(Resource.Loading)
            val event = repository.createEvent(
                title,
                type,
                groupIds,
                date,
                startTime,
                endTime,
                location,
                cloudUrl
            )
            emit(Resource.Success(event))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}
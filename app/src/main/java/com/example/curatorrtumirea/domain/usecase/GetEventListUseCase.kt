package com.example.curatorrtumirea.domain.usecase

import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.model.Event
import com.example.curatorrtumirea.domain.model.EventType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class GetEventListUseCase @Inject constructor() {

    operator fun invoke() = flow {
        emit(Resource.Loading)
        delay(3000)
        emit(Resource.Success(listOf(
            Event(
                id = 1L,
                title = "II Международная научно-практическая конференция цифровые международные отношения 2023",
                type = EventType.OFFSITE,
                date = Date()
            ),
            Event(
                id = 2L,
                title = "Круглый стол \"Перспективы развития цифровых технологий: опыт в России и за рубежом\"",
                type = EventType.FACE_TO_FACE,
            ),
            Event(
                id = 3L,
                title = "Конференция Flutter meetup",
                type = EventType.CAREER_GUIDANCE,
            ),
            Event(
                id = 4L,
                title = "Другое мероприятие",
                type = EventType.OTHER,
            ),
            Event(
                id = 1L,
                title = "II Международная научно-практическая конференция цифровые международные отношения 2023",
                type = EventType.OFFSITE,
                date = Date()
            ),
            Event(
                id = 2L,
                title = "Круглый стол \"Перспективы развития цифровых технологий: опыт в России и за рубежом\"",
                type = EventType.FACE_TO_FACE,
            ),
            Event(
                id = 3L,
                title = "Конференция Flutter meetup",
                type = EventType.CAREER_GUIDANCE,
            ),
            Event(
                id = 4L,
                title = "Другое мероприятие",
                type = EventType.OTHER,
            )
        )))
    }
}
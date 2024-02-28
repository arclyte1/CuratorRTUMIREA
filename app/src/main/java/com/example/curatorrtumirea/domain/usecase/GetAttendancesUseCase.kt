package com.example.curatorrtumirea.domain.usecase

import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.model.Attendance
import com.example.curatorrtumirea.domain.model.AttendanceGroup
import com.example.curatorrtumirea.domain.repository.AttendanceRepository
import com.example.curatorrtumirea.domain.repository.EventRepository
import com.example.curatorrtumirea.domain.repository.GroupRepository
import com.example.curatorrtumirea.domain.repository.StudentRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAttendancesUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val attendanceRepository: AttendanceRepository,
    private val mapRepositoryAttendanceGroupUseCase: MapRepositoryAttendanceGroupUseCase
) {

    operator fun invoke(eventId: Long) = flow {
        try {
            emit(Resource.Loading)
            val groupIds = eventRepository.getEventDetails(eventId)?.groupIds ?: emptyList()
            val attendances = attendanceRepository.getAttendances(eventId, groupIds)
            emit(Resource.Success(mapRepositoryAttendanceGroupUseCase(groupIds, attendances)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}
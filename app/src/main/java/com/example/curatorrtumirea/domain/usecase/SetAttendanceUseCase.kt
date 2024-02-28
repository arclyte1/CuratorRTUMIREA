package com.example.curatorrtumirea.domain.usecase

import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.repository.AttendanceRepository
import com.example.curatorrtumirea.domain.repository.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SetAttendanceUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val attendanceRepository: AttendanceRepository,
    private val mapRepositoryAttendanceGroupUseCase: MapRepositoryAttendanceGroupUseCase
) {

    operator fun invoke(
        eventId: Long,
        groupId: Long,
        studentId: Long,
        isPresent: Boolean
    ) = flow {
        try {
            emit(Resource.Loading)
            val groupIds = eventRepository.getEventDetails(eventId)?.groupIds ?: emptyList()
            val localAttendances = attendanceRepository.setLocalAttendance(eventId, groupIds, groupId, studentId, isPresent)
            emit(Resource.Success(mapRepositoryAttendanceGroupUseCase(groupIds, localAttendances)))
            val remoteAttendances = attendanceRepository.setRemoteAttendance(eventId, groupIds, groupId, studentId, isPresent)
            emit(Resource.Success(mapRepositoryAttendanceGroupUseCase(groupIds, remoteAttendances)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}
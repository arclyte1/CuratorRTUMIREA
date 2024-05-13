package com.example.curatorrtumirea.domain.usecase

import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.model.Attendance
import com.example.curatorrtumirea.domain.model.AttendanceGroup
import com.example.curatorrtumirea.domain.repository.EventRepository
import com.example.curatorrtumirea.domain.repository.GroupRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAttendancesUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val groupRepository: GroupRepository,
) {

    operator fun invoke(eventId: Long) = flow {
        try {
            emit(Resource.Loading)
            val event = eventRepository.getEventDetails(eventId)
            val groups = groupRepository.getGroupListByIds(false, event?.groupIds ?: emptyList())
            val attendanceGroups = groups.map { group ->
                AttendanceGroup(
                    groupId = group.id,
                    groupTitle = group.title,
                    attendances = group.students.map {
                        Attendance(
                            student = it,
                            isPresent = event?.presentStudents?.contains(it.id) ?: false,
                            isLoading = false
                        )
                    }
                )
            }

            emit(Resource.Success(attendanceGroups))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}
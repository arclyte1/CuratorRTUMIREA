package com.example.curatorrtumirea.domain.usecase

import android.util.Log
import com.example.curatorrtumirea.common.Resource
import com.example.curatorrtumirea.domain.model.Attendance
import com.example.curatorrtumirea.domain.model.AttendanceGroup
import com.example.curatorrtumirea.domain.model.Event
import com.example.curatorrtumirea.domain.repository.EventRepository
import com.example.curatorrtumirea.domain.repository.GroupRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetAttendanceUseCase @Inject constructor(
    private val eventRepository: EventRepository,
    private val groupRepository: GroupRepository,
) {

    private suspend fun mapEventToAttendanceGroups(
        event: Event,
        updatedStudentId: Long? = null
    ): List<AttendanceGroup> {
        val groups = groupRepository.getGroupListByIds(false, event.groupIds)
        return groups.map { group ->
            AttendanceGroup(
                groupId = group.id,
                groupTitle = group.title,
                attendances = group.students.map {
                    Attendance(
                        student = it,
                        isPresent = event.presentStudents.contains(it.id),
                        isLoading = it.id == updatedStudentId
                    )
                }
            )
        }
    }

    operator fun invoke(
        eventId: Long,
        studentId: Long,
        isPresent: Boolean
    ) = flow {
        var currentEvent: Event? = null
        try {
            emit(Resource.Loading)
            currentEvent = eventRepository.getEventDetails(eventId)
            if (currentEvent == null) {
                emit(Resource.Error("Event not found"))
                return@flow
            } else {
                var updatedPresentStudents = currentEvent.presentStudents - studentId
                if (isPresent) {
                    updatedPresentStudents += studentId
                }
                val updatedLocalEvent = currentEvent.copy(presentStudents = updatedPresentStudents)
                emit(Resource.Success(mapEventToAttendanceGroups(updatedLocalEvent, studentId)))

                val updatedRemoteEvent =
                    eventRepository.setAttendance(eventId, studentId, isPresent)
                Log.d("ABOBA", updatedRemoteEvent.toString())
                if (updatedRemoteEvent == null) {
                    emit(Resource.Success(mapEventToAttendanceGroups(currentEvent)))
                    emit(Resource.Error("Remote event not found"))
                } else {
                    emit(Resource.Success(mapEventToAttendanceGroups(updatedRemoteEvent)))
                }
            }
        } catch (e: Exception) {
            Log.e(this.javaClass.simpleName, e.localizedMessage ?: "e")
            currentEvent?.let { emit(Resource.Success(mapEventToAttendanceGroups(it))) }
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
        }
    }
}
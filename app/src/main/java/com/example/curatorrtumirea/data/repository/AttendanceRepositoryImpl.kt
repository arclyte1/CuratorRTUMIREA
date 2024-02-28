package com.example.curatorrtumirea.data.repository

import android.util.Log
import com.example.curatorrtumirea.domain.repository.AttendanceRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AttendanceRepositoryImpl @Inject constructor(

) : AttendanceRepository {

    // eventId to attendance groups
    private val attendances: MutableMap<Long, List<AttendanceRepository.AttendanceGroup>> =
        mutableMapOf()

    private suspend fun getRemoteAttendances(eventId: Long, groupIds: List<Long>) {
        Log.d(this.javaClass.simpleName, "getRemoteAttendances")
        delay(1000L)
        if (!attendances.containsKey(eventId)) {
            attendances[eventId] = emptyList()
        }
        attendances[eventId] = groupIds.map {
            AttendanceRepository.AttendanceGroup(
                groupId = it,
                attendances = listOf(
                    AttendanceRepository.Attendance(
                        studentId = 1L,
                        isPresent = false,
                        isLoading = false
                    ),
                    AttendanceRepository.Attendance(
                        studentId = 2L,
                        isPresent = true,
                        isLoading = false
                    ),
                    AttendanceRepository.Attendance(
                        studentId = 3L,
                        isPresent = false,
                        isLoading = false
                    ),
                )
            )
        }
    }

    override suspend fun getAttendances(
        eventId: Long,
        groupIds: List<Long>
    ): List<AttendanceRepository.AttendanceGroup> {
        if (!(attendances.containsKey(eventId) && groupIds.all { groupId ->
                attendances[eventId]?.any { it.groupId == groupId } == true
            })) {
            getRemoteAttendances(eventId, groupIds)
        }
        return attendances[eventId].orEmpty()
    }

    override suspend fun setLocalAttendance(
        eventId: Long,
        groupIds: List<Long>,
        studentGroupId: Long,
        studentId: Long,
        isPresent: Boolean
    ): List<AttendanceRepository.AttendanceGroup> {
        val attendances = getAttendances(eventId, groupIds).map { group ->
            if (group.groupId == studentGroupId) {
                group.copy(
                    attendances = group.attendances.map {
                        if (it.studentId == studentId) {
                            it.copy(
                                isPresent = isPresent,
                                isLoading = true
                            )
                        } else it
                    }
                )
            } else group
        }
        this.attendances[eventId] = attendances
        return attendances
    }

    override suspend fun setRemoteAttendance(
        eventId: Long,
        groupIds: List<Long>,
        studentGroupId: Long,
        studentId: Long,
        isPresent: Boolean
    ): List<AttendanceRepository.AttendanceGroup> {
        Log.d(this.javaClass.simpleName, "setRemoteAttendance")
        delay(500L)
        val attendances = getAttendances(eventId, groupIds).map { group ->
            if (group.groupId == studentGroupId) {
                group.copy(
                    attendances = group.attendances.map {
                        if (it.studentId == studentId) {
                            it.copy(
                                isPresent = isPresent,
                                isLoading = false
                            )
                        } else it
                    }
                )
            } else group
        }
        this.attendances[eventId] = attendances
        return attendances
    }
}
package com.example.curatorrtumirea.domain.repository

import kotlinx.coroutines.flow.Flow


interface AttendanceRepository {

    suspend fun getAttendances(eventId: Long, groupIds: List<Long>): List<AttendanceGroup>

    suspend fun setLocalAttendance(
        eventId: Long,
        groupIds: List<Long>,
        studentGroupId: Long,
        studentId: Long,
        isPresent: Boolean
    ): List<AttendanceGroup>

    suspend fun setRemoteAttendance(
        eventId: Long,
        groupIds: List<Long>,
        studentGroupId: Long,
        studentId: Long,
        isPresent: Boolean
    ): List<AttendanceGroup>

    data class Attendance(
        val studentId: Long,
        val isPresent: Boolean,
        val isLoading: Boolean,
    )

    data class AttendanceGroup(
        val groupId: Long,
        val attendances: List<Attendance>,
    )
}
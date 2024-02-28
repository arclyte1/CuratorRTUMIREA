package com.example.curatorrtumirea.domain.usecase

import com.example.curatorrtumirea.domain.model.Attendance
import com.example.curatorrtumirea.domain.model.AttendanceGroup
import com.example.curatorrtumirea.domain.repository.AttendanceRepository
import com.example.curatorrtumirea.domain.repository.EventRepository
import com.example.curatorrtumirea.domain.repository.GroupRepository
import com.example.curatorrtumirea.domain.repository.StudentRepository
import javax.inject.Inject

class MapRepositoryAttendanceGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
    private val studentRepository: StudentRepository,
) {

    suspend operator fun invoke(
        groupIds: List<Long>,
        attendanceGroups: List<AttendanceRepository.AttendanceGroup>
    ): List<AttendanceGroup> {
        val groups = groupRepository.getGroupListByIds(false, groupIds)
        val studentIds = mutableSetOf<Long>()
        attendanceGroups.forEach { group ->
            studentIds.addAll(group.attendances.map { it.studentId })
        }
        val students = studentRepository.getStudents(studentIds.toList())
        return attendanceGroups.map { group ->
            AttendanceGroup(
                groupId = group.groupId,
                groupTitle = groups.find { group.groupId == it.id }?.title ?: "",
                attendances = group.attendances.mapNotNull { attendance ->
                    students.find { it.id == attendance.studentId }?.let {
                        Attendance(
                            student = it,
                            isPresent = attendance.isPresent,
                            isLoading = attendance.isLoading
                        )
                    }
                }
            )
        }
    }
}
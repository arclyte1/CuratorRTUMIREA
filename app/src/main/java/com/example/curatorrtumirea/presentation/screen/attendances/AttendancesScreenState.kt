package com.example.curatorrtumirea.presentation.screen.attendances

import com.example.curatorrtumirea.domain.model.AttendanceGroup

data class AttendancesScreenState(
    val isLoading: Boolean = false,
    val attendances: List<AttendanceGroupItem> = emptyList(),
    val attendancesCountString: String = "",
) {

    fun copyWithChangedAttendances(attendances: List<AttendanceGroup>): AttendancesScreenState {
        val presentCount = attendances.sumOf { group -> group.attendances.count { it.isPresent } }
        val totalCount = attendances.sumOf { it.attendances.size }
        return this.copy(
            attendances = attendances.map { group ->
                AttendanceGroupItem(
                    this.attendances.find {
                        group.groupId == it.attendanceGroup.groupId
                    }?.isExpanded ?: true,
                    group
                )
            },
            attendancesCountString = "$presentCount/$totalCount"
        )
    }
}

sealed class AttendancesEffect {

}

sealed class AttendancesEvent {
    data class OnExpandedGroupChanged(val groupId: Long, val isExpanded: Boolean) : AttendancesEvent()
    data class OnAttendanceChanged(val studentId: Long, val groupId: Long, val isPresent: Boolean) : AttendancesEvent()
}

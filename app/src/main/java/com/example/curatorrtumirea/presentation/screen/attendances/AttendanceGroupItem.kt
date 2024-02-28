package com.example.curatorrtumirea.presentation.screen.attendances

import com.example.curatorrtumirea.domain.model.AttendanceGroup

data class AttendanceGroupItem(
    val isExpanded: Boolean = true,
    val attendanceGroup: AttendanceGroup
)

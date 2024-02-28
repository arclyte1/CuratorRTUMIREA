package com.example.curatorrtumirea.domain.model

data class AttendanceGroup(
    val groupId: Long,
    val groupTitle: String,
    val attendances: List<Attendance>,
)

data class Attendance(
    val student: Student,
    val isPresent: Boolean,
    val isLoading: Boolean,
)

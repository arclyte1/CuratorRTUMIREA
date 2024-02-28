package com.example.curatorrtumirea.domain.model

data class Group(
    val id: Long,
    val title: String,
    val students: List<Student> = emptyList(),
)

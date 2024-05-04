package com.example.curatorrtumirea.data.remote.dto

import com.example.curatorrtumirea.domain.model.Group
import kotlinx.serialization.Serializable

@Serializable
data class GroupDto(
    val id: Long,
    val title: String,
    val students: List<StudentDto>,
) {

    fun toGroup(): Group {
        return Group(
            id = this.id,
            title = this.title,
            students = this.students.map(StudentDto::toStudent)
        )
    }
}

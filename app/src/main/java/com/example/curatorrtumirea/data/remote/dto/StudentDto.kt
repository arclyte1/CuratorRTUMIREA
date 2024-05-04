package com.example.curatorrtumirea.data.remote.dto

import com.example.curatorrtumirea.domain.model.Student
import kotlinx.serialization.Serializable

@Serializable
data class StudentDto(
    val id: Long,
    val name: String,
) {

    fun toStudent(): Student {
        return Student(
            id = this.id,
            name = this.name
        )
    }
}

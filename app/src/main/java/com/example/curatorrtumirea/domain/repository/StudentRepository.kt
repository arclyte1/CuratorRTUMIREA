package com.example.curatorrtumirea.domain.repository

import com.example.curatorrtumirea.domain.model.Student

interface StudentRepository {

    suspend fun getStudents(studentIds: List<Long>): List<Student>
}
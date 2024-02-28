package com.example.curatorrtumirea.data.repository

import android.util.Log
import com.example.curatorrtumirea.domain.model.Student
import com.example.curatorrtumirea.domain.repository.StudentRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class StudentRepositoryImpl @Inject constructor() : StudentRepository {

    private var students: MutableList<Student> = mutableListOf()

    private suspend fun getRemoteStudents(studentIds: List<Long>): MutableList<Student> {
        Log.d(this.javaClass.simpleName, "getRemoteStudents")
        delay(1000L)
        return mutableListOf(
            Student(
                id = 1L,
                name = "Иванов И.И."
            ),
            Student(
                id = 2L,
                name = "Иванов И.И.2"
            ),
            Student(
                id = 3L,
                name = "Иванов И.И.3"
            ),
        )
    }

    override suspend fun getStudents(studentIds: List<Long>): List<Student> {
        if (!studentIds.all { id -> students.any { it.id == id } }) {
            students = getRemoteStudents(studentIds)
        }
        return students.filter { it.id in studentIds }
    }
}
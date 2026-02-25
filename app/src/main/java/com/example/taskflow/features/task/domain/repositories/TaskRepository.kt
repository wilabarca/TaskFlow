package com.example.taskflow.features.task.domain.repositories

import com.example.taskflow.features.task.domain.entities.Task
import kotlinx.coroutines.flow.StateFlow

interface TaskRepository {
    val tasks: StateFlow<List<Task>>
    suspend fun post(newTask: Task): Result<String>
    fun get(): StateFlow<List<Task>>
    suspend fun update(modifiedTask: Task, index: UInt): Result<String>
    suspend fun delete(index: UInt): Result<String>
}
package com.example.taskflow.features.task.data.repositories

import com.example.taskflow.features.task.domain.entities.Task
import com.example.taskflow.features.task.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor() : TaskRepository {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    override val tasks = _tasks.asStateFlow()

    override fun get(): StateFlow<List<Task>> = tasks
    override suspend fun post(newTask: Task): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun update(modifiedTask: Task, index: UInt): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(index: UInt): Result<String> {
        TODO("Not yet implemented")
    }
}
package com.example.taskflow.features.task.domain.usecases

import com.example.taskflow.features.task.domain.entities.Task
import com.example.taskflow.features.task.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class WatchTasksUseCase (
    private val repository: TaskRepository
) {
    operator fun invoke(): StateFlow<List<Task>> = repository.tasks
}
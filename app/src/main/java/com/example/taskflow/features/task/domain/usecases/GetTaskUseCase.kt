package com.example.taskflow.features.task.domain.usecases

import com.example.taskflow.features.task.domain.entities.Task
import com.example.taskflow.features.task.domain.repositories.TaskRepository
import kotlinx.coroutines.flow.StateFlow

class GetTaskUseCase (
    private val repository: TaskRepository
) {
    operator fun invoke(): StateFlow<List<Task>> = repository.tasks
}
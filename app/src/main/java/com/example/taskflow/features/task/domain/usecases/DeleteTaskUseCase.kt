package com.example.taskflow.features.task.domain.usecases

import com.example.taskflow.features.task.domain.repositories.TaskRepository

class DeleteTaskUseCase (
    private val repository: TaskRepository
) {
    suspend operator fun invoke(index: UInt): Result<String>{
        return repository.delete(index)
    }
}
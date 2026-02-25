package com.example.taskflow.features.task.domain.usecases

import com.example.taskflow.features.task.domain.entities.Task
import com.example.taskflow.features.task.domain.repositories.TaskRepository

class PostTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(titulo: String, descripcion: String, estatus: String): Result<String> {
        val newtask = Task(
            titulo, descripcion, estatus
        )
        return repository.post(newtask)
    }
}
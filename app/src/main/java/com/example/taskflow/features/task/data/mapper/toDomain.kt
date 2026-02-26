package com.example.taskflow.features.task.data.mapper

import com.example.taskflow.features.task.data.models.TaskModel
import com.example.taskflow.features.task.domain.entities.Task

fun TaskModel.toDomain(): Task {
    return Task(
        titulo = this.titulo,
        descripcion = this.descripcion,
        estatus = this.estatus
    )
}
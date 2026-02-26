package com.example.taskflow.features.task.data.mapper

import com.example.taskflow.features.task.data.models.TaskModel
import com.example.taskflow.features.task.domain.entities.Task

fun Task.toInfra(): TaskModel {
    return TaskModel(
        titulo = this.titulo,
        descripcion = this.descripcion,
        estatus = this.estatus
    )
}
package com.example.taskflow.features.task.data.models

import kotlinx.serialization.Serializable

@Serializable
data class TaskModel(
    val titulo: String,
    val descripcion: String,
    val estatus: String
)

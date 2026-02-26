package com.example.taskflow.features.task.data.models

import kotlinx.serialization.Serializable

@Serializable
data class TaskMessage(
    val action: String,
    val task: TaskModel? = null,
    val index: UInt? = null
)
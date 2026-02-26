package com.example.taskflow.features.task.data.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("type")
sealed class ServerMessage {
    @Serializable
    @SerialName("tasks")
    data class TaskList(val tasks: List<TaskModel>) : ServerMessage()

    @Serializable
    @SerialName("error")
    data class Error(val message: String) : ServerMessage()

    @Serializable
    @SerialName("ok")
    data class Ok(val message: String) : ServerMessage()
}
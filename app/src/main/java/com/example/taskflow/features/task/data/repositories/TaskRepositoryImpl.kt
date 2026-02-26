package com.example.taskflow.features.task.data.repositories

import com.example.taskflow.features.task.data.mapper.toDomain
import com.example.taskflow.features.task.data.mapper.toInfra
import com.example.taskflow.features.task.data.models.ServerMessage
import com.example.taskflow.features.task.data.models.TaskMessage
import com.example.taskflow.features.task.domain.entities.Task
import com.example.taskflow.features.task.domain.repositories.TaskRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val client: HttpClient
) : TaskRepository {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    override val tasks = _tasks.asStateFlow()

    private var session: DefaultWebSocketSession? = null
    private val pendingRequests = ConcurrentHashMap<String, CompletableDeferred<Result<String>>>()
    private val json = Json {
        ignoreUnknownKeys = true
        classDiscriminator = "type"
    }
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        scope.launch { maintainConnection() }
    }

    private suspend fun maintainConnection() {
        while (true) {
            try {
                client.webSocket(
                    method = HttpMethod.Get,
                    host = "192.168.0.2",
                    port = 8080,
                    path = "/tasks"
                ) {
                    session = this
                    for (frame in incoming) {
                        if (frame is Frame.Text) {
                            handleServerMessage(frame.readText())
                        }
                    }
                }
            } catch (e: Exception) {
                session = null
            } finally {
                session = null
                pendingRequests.values.forEach {
                    it.complete(Result.failure(Exception("Conexión perdida")))
                }
                pendingRequests.clear()
                delay(3_000)
            }
        }
    }

    private fun handleServerMessage(raw: String) {
        try {
            val message = json.decodeFromString<ServerMessage>(raw)
            when (message) {
                is ServerMessage.TaskList -> _tasks.value = message.tasks.map { it.toDomain() }
                is ServerMessage.Ok -> pendingRequests.remove(message.message)
                    ?.complete(Result.success(message.message))
                is ServerMessage.Error -> pendingRequests.remove(message.message)
                    ?.complete(Result.failure(Exception(message.message)))
            }
        } catch (_: Exception) {}
    }

    private suspend fun sendMessage(requestId: String, payload: TaskMessage): Result<String> {
        val currentSession = session
            ?: return Result.failure(Exception("Sin conexión al servidor"))

        val deferred = CompletableDeferred<Result<String>>()
        pendingRequests[requestId] = deferred

        return try {
            currentSession.send(Frame.Text(json.encodeToString(payload)))
            withTimeout(10_000) { deferred.await() }
        } catch (e: TimeoutCancellationException) {
            pendingRequests.remove(requestId)
            Result.failure(Exception("Tiempo de espera agotado"))
        } catch (e: Exception) {
            pendingRequests.remove(requestId)
            Result.failure(e)
        }
    }

    override suspend fun post(newTask: Task): Result<String> {
        val id = "post_${System.currentTimeMillis()}"
        return sendMessage(id, TaskMessage(action = "post", task = newTask.toInfra()))
    }

    override suspend fun update(modifiedTask: Task, index: UInt): Result<String> {
        val id = "update_${System.currentTimeMillis()}"
        return sendMessage(id, TaskMessage(action = "update", task = modifiedTask.toInfra(), index = index))
    }

    override suspend fun delete(index: UInt): Result<String> {
        val id = "delete_${System.currentTimeMillis()}"
        return sendMessage(id, TaskMessage(action = "delete", index = index))
    }
}
package com.example.taskflow.features.task.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskflow.features.task.domain.entities.Task
import com.example.taskflow.features.task.domain.usecases.DeleteTaskUseCase
import com.example.taskflow.features.task.domain.usecases.PutTaskUseCase
import com.example.taskflow.features.task.domain.usecases.WatchTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val deleteTaskUseCase: DeleteTaskUseCase,
    watchTasksUseCase: WatchTasksUseCase
) : ViewModel() {

    val tasks = watchTasksUseCase()

    private val _editTarget = MutableStateFlow<Pair<UInt, Task>?>(null)
    val editTarget = _editTarget.asStateFlow()

    fun onAgregarTarea(navigate: () -> Unit) {
        navigate()
    }

    fun onEditarTarea(index: UInt, task: Task) {
        _editTarget.value = Pair(index, task)
    }

    fun onDismissEdicion() {
        _editTarget.value = null
    }

    fun onEliminarTarea(index: UInt) {
        viewModelScope.launch {
            deleteTaskUseCase(index)
        }
    }
}
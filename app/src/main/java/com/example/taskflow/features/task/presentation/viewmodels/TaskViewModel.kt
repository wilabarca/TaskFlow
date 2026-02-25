package com.example.taskflow.features.task.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.taskflow.features.task.domain.entities.Task
import com.example.taskflow.features.task.domain.usecases.DeleteTaskUseCase
import com.example.taskflow.features.task.domain.usecases.GetTaskUseCase
import com.example.taskflow.features.task.domain.usecases.PutTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    putTaskUseCase: PutTaskUseCase,
    deleteTaskUseCase: DeleteTaskUseCase,
    getTaskUseCase: GetTaskUseCase
) : ViewModel() {
    val tasks = getTaskUseCase()

    fun onAgregarTarea() {
        TODO()
    }
}
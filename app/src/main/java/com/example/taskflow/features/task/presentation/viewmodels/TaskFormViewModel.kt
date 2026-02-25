package com.example.taskflow.features.task.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskflow.features.task.domain.usecases.PostTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskFormViewModel @Inject constructor(
    private val postTaskUseCase: PostTaskUseCase
) : ViewModel() {
    private val _titulo = MutableStateFlow("")
    val titulo = _titulo.asStateFlow()

    fun setTitulo(value: String) {
        _titulo.value = value
    }

    private val _estatus = MutableStateFlow("")
    val estatus = _estatus.asStateFlow()

    fun setEstatus(value: String) {
        _estatus.value = value
    }

    private val _descripcion = MutableStateFlow("")
    val descripcion = _descripcion.asStateFlow()

    fun setDesc(value: String) {
        _descripcion.value = value
    }

    private val _expanded = MutableStateFlow(false)
    val expanded = _expanded.asStateFlow()

    fun toggleExpanded() {
        _expanded.value = !expanded.value
    }

    fun toggleOffExpanded() {
        _expanded.value = false
    }

    fun onAgregar() {
        viewModelScope.launch {
            postTaskUseCase(titulo.value, descripcion.value, estatus.value)
            _titulo.value = ""
            _descripcion.value = ""
            _estatus.value = ""
        }
    }
}
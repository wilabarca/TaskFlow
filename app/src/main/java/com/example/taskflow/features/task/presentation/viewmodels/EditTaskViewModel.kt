package com.example.taskflow.features.task.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskflow.features.task.domain.usecases.PutTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val putTaskUseCase: PutTaskUseCase
) : ViewModel() {

    private val _titulo = MutableStateFlow("")
    val titulo = _titulo.asStateFlow()

    private val _estatus = MutableStateFlow("")
    val estatus = _estatus.asStateFlow()

    private val _descripcion = MutableStateFlow("")
    val descripcion = _descripcion.asStateFlow()

    private val _expanded = MutableStateFlow(false)
    val expanded = _expanded.asStateFlow()

    private var currentIndex: UInt = 0u

    fun cargarTarea(titulo: String, descripcion: String, estatus: String, index: UInt) {
        _titulo.value = titulo
        _descripcion.value = descripcion
        _estatus.value = estatus
        currentIndex = index
    }

    fun setTitulo(value: String) { _titulo.value = value }
    fun setEstatus(value: String) { _estatus.value = value }
    fun setDesc(value: String) { _descripcion.value = value }
    fun toggleExpanded() { _expanded.value = !_expanded.value }
    fun toggleOffExpanded() { _expanded.value = false }

    fun onGuardar(onSuccess: () -> Unit) {
        viewModelScope.launch {
            putTaskUseCase(_titulo.value, _descripcion.value, _estatus.value, currentIndex)
            onSuccess()
        }
    }
}
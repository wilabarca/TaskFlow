package com.example.taskflow.features.task.presentation.screeens



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.taskflow.features.task.presentation.components.TaskForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(padding)
        ) {
            TaskForm(
                onAgregar = { titulo, estatus, descripcion ->
                    // Aquí mañana conectas el ViewModel
                }
            )
        }
    }
}
package com.example.taskflow.features.task.presentation.screeens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskflow.features.task.presentation.components.PurpleDark
import com.example.taskflow.features.task.presentation.components.TaskHome
import com.example.taskflow.features.task.presentation.components.Purple

data class TaskPreview(
    val titulo: String,
    val descripcion: String,
    val estatus: String
)

val tareasEjemplo = listOf(
    TaskPreview("App Moviles", "Crear un app de clima", "Inicio"),
    TaskPreview("App Moviles", "", "En proceso"),
    TaskPreview("App Moviles", "", "Finalizado")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    onAgregarTarea: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Home",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PurpleDark
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAgregarTarea,
                containerColor = Purple,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar tarea"
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFEEEFF8),
                            Color(0xFFF5F5FF)
                        )
                    )
                )
                .padding(padding)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tareas",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = PurpleDark
                )
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Purple.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "${tareasEjemplo.size} tareas",
                        fontSize = 13.sp,
                        color = PurpleDark,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(tareasEjemplo) { tarea ->
                    TaskHome(
                        titulo = tarea.titulo,
                        descripcion = tarea.descripcion,
                        estatus = tarea.estatus,
                        onEliminar = { },
                        onActualizar = { }
                    )
                }
            }
        }
    }
}
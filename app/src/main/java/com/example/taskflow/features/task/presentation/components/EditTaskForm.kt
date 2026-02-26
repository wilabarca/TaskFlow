package com.example.taskflow.features.task.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskflow.features.task.presentation.viewmodels.EditTaskViewModel

@Composable
fun EditTaskForm(
    index: UInt,
    titulo: String,
    descripcion: String,
    estatus: String,
    onDismiss: () -> Unit,
    viewmodel: EditTaskViewModel = hiltViewModel()
) {
    LaunchedEffect(index) {
        viewmodel.cargarTarea(titulo, descripcion, estatus, index)
    }

    val tituloState by viewmodel.titulo.collectAsStateWithLifecycle()
    val estatusState by viewmodel.estatus.collectAsStateWithLifecycle()
    val descripcionState by viewmodel.descripcion.collectAsStateWithLifecycle()
    val expanded by viewmodel.expanded.collectAsStateWithLifecycle()

    val opciones = listOf("Inicio", "En proceso", "Finalizado")

    val estatusColor = when (estatusState) {
        "Inicio" -> Color(0xFF4CAF50)
        "En proceso" -> Color(0xFFFF9800)
        "Finalizado" -> Color(0xFF2196F3)
        else -> Color.Gray
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(12.dp),
            colors = CardDefaults.cardColors(containerColor = BackgroundCard)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(PurpleDark, Purple, PurpleLight)
                            )
                        )
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Editar Tarea",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Título",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = PurpleDark
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = tituloState,
                    onValueChange = viewmodel::setTitulo,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Escribe el título", color = Color.LightGray) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Purple,
                        unfocusedBorderColor = Color(0xFFDDDDDD),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Estatus",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = PurpleDark
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .border(
                                width = 1.dp,
                                color = if (expanded) Purple else Color(0xFFDDDDDD),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { viewmodel.toggleExpanded() }
                            .padding(horizontal = 16.dp, vertical = 14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (estatusState.isNotEmpty()) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .clip(RoundedCornerShape(50))
                                            .background(estatusColor)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                                Text(
                                    text = if (estatusState.isEmpty()) "Selecciona un estatus" else estatusState,
                                    color = if (estatusState.isEmpty()) Color.LightGray else Color.DarkGray,
                                    fontSize = 15.sp
                                )
                            }
                            Icon(
                                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = Purple
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { viewmodel.toggleOffExpanded() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {
                        opciones.forEach { opcion ->
                            val color = when (opcion) {
                                "Inicio" -> Color(0xFF4CAF50)
                                "En proceso" -> Color(0xFFFF9800)
                                "Finalizado" -> Color(0xFF2196F3)
                                else -> Color.Gray
                            }
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(10.dp)
                                                .clip(RoundedCornerShape(50))
                                                .background(color)
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(text = opcion, fontSize = 15.sp)
                                    }
                                },
                                onClick = {
                                    viewmodel.setEstatus(opcion)
                                    viewmodel.toggleOffExpanded()
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Descripción",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = PurpleDark
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = descripcionState,
                    onValueChange = viewmodel::setDesc,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    placeholder = { Text("Escribe la descripción", color = Color.LightGray) },
                    maxLines = 5,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Purple,
                        unfocusedBorderColor = Color(0xFFDDDDDD),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewmodel.onGuardar(onDismiss) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(PurpleDark, Purple, PurpleLight)
                                ),
                                shape = RoundedCornerShape(14.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Guardar cambios",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }
    }
}
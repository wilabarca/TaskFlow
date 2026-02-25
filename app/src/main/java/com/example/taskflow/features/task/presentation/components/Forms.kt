package com.example.taskflow.features.task.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val Purple = Color(0xFF7B7FC4)
val PurpleLight = Color(0xFFB0B4E8)
val PurpleDark = Color(0xFF4A4F9C)
val BackgroundCard = Color(0xFFFAFAFF)

@Composable
fun TaskForm(
    onAgregar: (titulo: String, estatus: String, descripcion: String) -> Unit = { _, _, _ -> }
) {
    var titulo by remember { mutableStateOf("") }
    var estatus by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val opciones = listOf("Inicio", "En proceso", "Finalizado")

    val estatusColor = when (estatus) {
        "Inicio" -> Color(0xFF4CAF50)
        "En proceso" -> Color(0xFFFF9800)
        "Finalizado" -> Color(0xFF2196F3)
        else -> Color.Gray
    }

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
                    text = "Crear Tarea",
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
                value = titulo,
                onValueChange = { titulo = it },
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
                        .clickable { expanded = !expanded }
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (estatus.isNotEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(RoundedCornerShape(50))
                                        .background(estatusColor)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text(
                                text = if (estatus.isEmpty()) "Selecciona un estatus" else estatus,
                                color = if (estatus.isEmpty()) Color.LightGray else Color.DarkGray,
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
                    onDismissRequest = { expanded = false },
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
                                estatus = opcion
                                expanded = false
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
                value = descripcion,
                onValueChange = { descripcion = it },
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
                onClick = { onAgregar(titulo, estatus, descripcion) },
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
                        text = "Agregar Tarea",
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
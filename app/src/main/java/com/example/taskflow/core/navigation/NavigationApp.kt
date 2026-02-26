package com.example.taskflow.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskflow.features.task.presentation.screeens.FormScreen
import com.example.taskflow.features.task.presentation.screeens.TaskScreen

@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "Home") {
        composable("Home") {
            TaskScreen(
                onNavigateToForm = {navController.navigate("PostForm")}
            )
        }
        composable("PostForm") {
            FormScreen()
        }
    }
}
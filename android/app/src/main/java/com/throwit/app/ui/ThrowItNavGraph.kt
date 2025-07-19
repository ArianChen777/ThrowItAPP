package com.throwit.app.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.throwit.app.ui.main.MainScreen

@Composable
fun ThrowItNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen(navController = navController)
        }
    }
}
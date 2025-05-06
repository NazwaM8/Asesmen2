package com.nazwamursyidan0077.asesmen2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nazwamursyidan0077.asesmen2.ui.screen.DetailScreen
import com.nazwamursyidan0077.asesmen2.ui.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen()
        }
        composable(route = Screen.Formbaru.route) {
            DetailScreen()
        }
    }
}
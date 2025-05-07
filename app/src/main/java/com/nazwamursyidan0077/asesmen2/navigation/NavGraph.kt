package com.nazwamursyidan0077.asesmen2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nazwamursyidan0077.asesmen2.ui.screen.DetailScreen
import com.nazwamursyidan0077.asesmen2.ui.screen.KEY_ID_ANIDRAMA
import com.nazwamursyidan0077.asesmen2.ui.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.FormBaru.route) {
            DetailScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_ANIDRAMA) {type = NavType.LongType}
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_ANIDRAMA)
            DetailScreen(navController, id)
        }
    }
}
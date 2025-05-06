package com.nazwamursyidan0077.asesmen2.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object Formbaru: Screen("detailScreen")
}
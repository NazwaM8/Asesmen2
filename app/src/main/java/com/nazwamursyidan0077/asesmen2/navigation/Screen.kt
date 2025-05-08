package com.nazwamursyidan0077.asesmen2.navigation

import com.nazwamursyidan0077.asesmen2.ui.screen.KEY_ID_ANIDRAMA

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object FormBaru: Screen("detailScreen")
    data object Trash: Screen("trashScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_ANIDRAMA}") {
        fun withId(id: Long) = "detailScreen/$id"
    }

}
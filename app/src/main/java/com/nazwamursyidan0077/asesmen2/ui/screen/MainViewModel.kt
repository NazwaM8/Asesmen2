package com.nazwamursyidan0077.asesmen2.ui.screen

import androidx.lifecycle.ViewModel
import com.nazwamursyidan0077.asesmen2.model.AniDrama

class MainViewModel : ViewModel() {

    val data = listOf(
        AniDrama(
            1,
            "My Hero Academia",
            "Series",
            13,
            2016,
            9
        ),
        AniDrama(
            2,
            "Love Revolution",
            "Series",
            30,
            2020,
            9
        ),
        AniDrama(
            3,
            "Weak Hero Class 1",
            "Series",
            8,
            2022,
            9
        )
    )
}
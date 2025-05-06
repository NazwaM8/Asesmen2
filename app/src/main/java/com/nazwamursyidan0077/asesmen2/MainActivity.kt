package com.nazwamursyidan0077.asesmen2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nazwamursyidan0077.asesmen2.navigation.SetupNavGraph
import com.nazwamursyidan0077.asesmen2.ui.theme.Asesmen2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Asesmen2Theme {
                SetupNavGraph()
            }
        }
    }
}


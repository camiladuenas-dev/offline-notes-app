package com.example.offlinenotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.navigation.compose.rememberNavController
import com.example.offlinenotes.ui.theme.OfflineNotesTheme
import com.example.offlinenotes.presentation.navigation.Navigation



@OptIn(ExperimentalMaterial3Api::class)
@dagger.hilt.android.AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OfflineNotesTheme {
                val navController = rememberNavController()
                Navigation(navController = navController)
            }
        }
    }
}


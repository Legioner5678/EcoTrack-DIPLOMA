package com.example.ecotrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.ecotrack.ui.theme.EcoTrackTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // ОБЯЗАТЕЛЬНО для Hilt
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoTrackTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        // ВЫЗЫВАЕМ ТВОЮ ФУНКЦИЮ ИЗ ФАЙЛА BottomNavBar.kt
                        // Теперь всё управление кнопками будет в одном месте
                        BottomNavigationBar(navController = navController)
                    }
                ) { innerPadding ->
                    NavigationGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
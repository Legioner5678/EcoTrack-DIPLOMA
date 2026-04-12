package com.example.ecotrack

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home // Вернул стандартную
import androidx.compose.material.icons.filled.Check // Замена на простую галочку
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val label: String
)

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(Screen.News.route, Icons.Default.Home, "News"),
        BottomNavItem(Screen.Habits.route, Icons.Default.Check, "Habits"),
        BottomNavItem(Screen.Map.route, Icons.Default.Place, "Map"),
        BottomNavItem(Screen.Profile.route, Icons.Default.Person, "Profile"),
        BottomNavItem(Screen.Settings.route, Icons.Default.Settings, "Settings")
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
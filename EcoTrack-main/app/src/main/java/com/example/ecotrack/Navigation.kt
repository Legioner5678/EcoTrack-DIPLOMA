package com.example.ecotrack

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ecotrack.ui.screens.news.NewsScreen
import com.example.ecotrack.ui.habits.HabitTrackerScreen
import com.example.ecotrack.ui.map.EcoMapScreen
import com.example.ecotrack.ui.profile.ProfileScreen
import com.example.ecotrack.ui.settings.SettingsScreen
import com.example.ecotrack.ui.screens.ChatScreen

sealed class Screen(val route: String) {
    data object News : Screen("home")
    data object Habits : Screen("habits")
    data object Profile : Screen("profile")
    data object Settings : Screen("settings")
    data object Chat : Screen("chat")
    data object Map : Screen("map")
}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.News.route,
        modifier = modifier
    ) {
        composable(Screen.News.route) { NewsScreen(navController) }
        composable(Screen.Habits.route) { HabitTrackerScreen() }
        composable(Screen.Map.route) { EcoMapScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
        composable(Screen.Settings.route) { SettingsScreen() }
        composable(Screen.Chat.route) { ChatScreen() }
    }
}
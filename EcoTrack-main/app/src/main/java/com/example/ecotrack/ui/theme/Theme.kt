package com.example.ecotrack.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = LightGreen,
    onPrimary = DarkGreen,
    secondary = SkyBlue,
    tertiary = PrimaryGreen,
    primaryContainer = DarkGreen,
    onPrimaryContainer = LightGreen,
    secondaryContainer = Color(0xFF0B2A33),
    onSecondaryContainer = Color(0xFFCDEEF8),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E)
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = Color.White,
    secondary = SkyBlue,
    onSecondary = Color(0xFF002A3A),
    tertiary = DarkGreen,
    primaryContainer = LightGreen,
    onPrimaryContainer = DarkGreen,
    secondaryContainer = SkyBlueSoft,
    onSecondaryContainer = Color(0xFF003344),
    background = Color(0xFFF8FBF8),
    surface = Color.White
)

@Composable
fun EcoTrackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Отключаем динамические цвета для сохранения брендинга
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
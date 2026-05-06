package com.example.chefconnect.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Colores del diseño
val PrimaryGreen = Color(0xFF4CAF50)
val SecondaryGreen = Color(0xFF2E7D32)
val LightGreenBackground = Color(0xFFEBF3EF)
val White = Color(0xFFFFFFFF)

@Composable
fun ChefConnectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = PrimaryGreen,
            secondary = SecondaryGreen,
            tertiary = LightGreenBackground,
            background = Color(0xFF121212),
            surface = Color(0xFF1E1E1E),
            onPrimary = White,
            onSecondary = White,
        )
    } else {
        lightColorScheme(
            primary = PrimaryGreen,
            secondary = SecondaryGreen,
            tertiary = LightGreenBackground,
            background = LightGreenBackground,
            surface = White,
            onPrimary = White,
            onSecondary = White,
            onBackground = Color(0xFF1C1B1F),
            onSurface = Color(0xFF1C1B1F),
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
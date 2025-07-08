package com.android.design_system

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


data class ThemeColorsTemplate(
    private val darkTheme: Boolean,
    val light: ColorScheme,
    val dark: ColorScheme
) {
    val colors: ColorScheme
        get() = if (darkTheme) dark else light
}

private val LightColors = lightColorScheme()

private val DarkColors = darkColorScheme(
    primary = Color(0xFFD6F12C),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF1C1C1E),
    onPrimaryContainer = Color.White,

    secondary = Color(0xFF00C853),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF2A2A2C),
    onSecondaryContainer = Color.White,

    background = Color(0xFF121212),
    onBackground = Color.White,

    surface = Color(0xFF1C1C1E),
    onSurface = Color.White,

    error = Color(0xFFB00020),
    onError = Color.White,

    outline = Color(0xFFBDBDBD)
)

@Composable
fun themeColors(darkTheme: Boolean): ThemeColorsTemplate {
    return ThemeColorsTemplate(
        darkTheme = darkTheme,
        light = LightColors,
        dark = DarkColors
    )
}
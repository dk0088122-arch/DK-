package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = WarriorBlueContainer,
    onPrimary = WarriorBlueDeep,
    primaryContainer = WarriorBlueDark,
    onPrimaryContainer = Color.White,
    secondary = WarriorBlueBorder,
    onSecondary = WarriorBlueDeep,
    background = Color(0xFF121316),
    surface = Color(0xFF1E2024),
    onBackground = Color(0xFFE2E2E6),
    onSurface = Color(0xFFE2E2E6),
    outline = WarriorBlueBorder
  )

private val LightColorScheme =
  lightColorScheme(
    primary = WarriorBlue,
    onPrimary = Color.White,
    primaryContainer = WarriorBlueContainer,
    onPrimaryContainer = WarriorBlueDeep,
    secondary = TextSecondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD7E3F7),
    onSecondaryContainer = WarriorBlueDeep,
    background = BackgroundLight,
    surface = SurfaceLight,
    surfaceVariant = Color(0xFFDFE2EB),
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    outline = WarriorBlueBorder,
    outlineVariant = TextMuted,
    error = ErrorRed,
    errorContainer = ErrorContainer
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep branded Bold Typography theme consistent
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}


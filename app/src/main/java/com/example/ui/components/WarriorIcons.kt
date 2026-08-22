package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.WarriorBlue
import com.example.ui.theme.WarriorDiamondCyan
import com.example.ui.theme.WarriorDiamondGold

@Composable
fun DiamondVectorIcon(
  modifier: Modifier = Modifier,
  size: Dp = 48.dp,
  primaryColor: Color = Color.White,
  accentColor: Color = WarriorDiamondCyan
) {
  Canvas(modifier = modifier.size(size)) {
    val w = this.size.width
    val h = this.size.height

    val path = Path().apply {
      moveTo(w * 0.5f, h * 0.08f)
      lineTo(w * 0.92f, h * 0.32f)
      lineTo(w * 0.5f, h * 0.92f)
      lineTo(w * 0.08f, h * 0.32f)
      close()
    }

    drawPath(path = path, color = primaryColor, style = Fill)

    // Inner facets
    val innerLines = Path().apply {
      // Top facets
      moveTo(w * 0.28f, h * 0.32f)
      lineTo(w * 0.5f, h * 0.08f)
      lineTo(w * 0.72f, h * 0.32f)

      // Center down
      moveTo(w * 0.28f, h * 0.32f)
      lineTo(w * 0.5f, h * 0.92f)
      lineTo(w * 0.72f, h * 0.32f)

      // Horizontal girdle
      moveTo(w * 0.08f, h * 0.32f)
      lineTo(w * 0.92f, h * 0.32f)
    }

    drawPath(
      path = innerLines,
      color = accentColor.copy(alpha = 0.75f),
      style = Stroke(width = w * 0.04f)
    )
  }
}

@Composable
fun WarriorShieldIcon(
  modifier: Modifier = Modifier,
  size: Dp = 48.dp,
  color: Color = Color.White
) {
  Canvas(modifier = modifier.size(size)) {
    val w = this.size.width
    val h = this.size.height

    val path = Path().apply {
      moveTo(w * 0.5f, h * 0.1f)
      lineTo(w * 0.85f, h * 0.25f)
      lineTo(w * 0.85f, h * 0.6f)
      quadraticTo(w * 0.85f, h * 0.85f, w * 0.5f, h * 0.95f)
      quadraticTo(w * 0.15f, h * 0.85f, w * 0.15f, h * 0.6f)
      lineTo(w * 0.15f, h * 0.25f)
      close()
    }

    drawPath(path = path, color = color, style = Fill)

    // Shield emblem cross
    drawCircle(
      color = WarriorBlue,
      radius = w * 0.14f,
      center = Offset(w * 0.5f, h * 0.5f)
    )
  }
}

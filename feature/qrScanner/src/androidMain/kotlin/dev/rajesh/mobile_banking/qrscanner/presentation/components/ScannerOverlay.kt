package dev.rajesh.mobile_banking.qrscanner.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ScannerOverlay(
    isPaused: Boolean,
    modifier: Modifier = Modifier
) {
    // 1. Laser Line Animation
    val infiniteTransition = rememberInfiniteTransition(label = "LaserTransition")

    // The line moves from 0f (top) to 1f (bottom)
    val lineProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "LaserLine"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        val boxSize = size.width * 0.7f
        val boxWidth = width * 0.7f
        val boxHeight = width * 0.7f
        val left = (width - boxSize) / 2
        val top = (height - boxSize) / 2
        val rect = Rect(left, top, left + boxSize, top + boxSize)

        // 2. Draw the Dimmed Background
        // We draw a black layer and then "pierce" a hole through it using BlendMode.Clear
        drawRect(color = Color.Black.copy(alpha = 0.6f))

        drawRoundRect(
            color = Color.Transparent,
            topLeft = rect.topLeft,
            size = rect.size,
            cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx()),
            blendMode = BlendMode.Clear
        )

        // 3. Draw the Border (Green when active, Gray when paused)
        val borderColor = if (isPaused) Color.LightGray else Color(0xFF00FF00)
        drawRoundRect(
            color = borderColor,
            topLeft = rect.topLeft,
            size = rect.size,
            cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx()),
            style = Stroke(width = 1.dp.toPx())
        )

        // 4. Draw the Animated Laser Line (only if not paused)
        if (!isPaused) {
            val yOffset = top + (boxHeight * lineProgress)
            drawLine(
                color = Color.Red,
                start = Offset(left + 10.dp.toPx(), yOffset),
                end = Offset(left + boxWidth - 10.dp.toPx(), yOffset),
                strokeWidth = 2.dp.toPx(),
                alpha = 0.7f
            )
        }

        // 5. Draw 4 Corner Bracket
        val bracketColor = if (isPaused) Color.Gray else Color.Green
        val strokeWidth = 4.dp.toPx()
        val lineLength = 24.dp.toPx()

        val path = Path().apply {
            // Top Left
            moveTo(left, top + lineLength)
            lineTo(left, top)
            lineTo(left + lineLength, top)

            // Top Right
            moveTo(left + boxSize - lineLength, top)
            lineTo(left + boxSize, top)
            lineTo(left + boxSize, top + lineLength)

            // Bottom Right
            moveTo(left + boxSize, top + boxSize - lineLength)
            lineTo(left + boxSize, top + boxSize)
            lineTo(left + boxSize - lineLength, top + boxSize)

            // Bottom Left
            moveTo(left + lineLength, top + boxSize)
            lineTo(left, top + boxSize)
            lineTo(left, top + boxSize - lineLength)
        }

        drawPath(path = path, color = bracketColor, style = Stroke(strokeWidth))
    }
}
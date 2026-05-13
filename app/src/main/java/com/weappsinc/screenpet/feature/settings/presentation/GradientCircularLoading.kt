package com.weappsinc.screenpet.feature.settings.presentation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Vong tron xoay voi gradient hong -> tim (token brand). */
@Composable
fun GradientCircularLoading(
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    strokeWidth: Dp = 6.dp,
) {
    val transition = rememberInfiniteTransition(label = "locale-loading")
    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1100, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "angle",
    )
    Canvas(modifier = modifier.size(size).rotate(angle)) {
        val strokePx = strokeWidth.toPx()
        val side = size.toPx() - strokePx
        drawArc(
            brush = Brush.linearGradient(LOADING_GRADIENT),
            startAngle = -90f,
            sweepAngle = 280f,
            useCenter = false,
            topLeft = Offset(strokePx / 2f, strokePx / 2f),
            size = Size(side, side),
            style = Stroke(width = strokePx, cap = StrokeCap.Round),
        )
    }
}

private val LOADING_GRADIENT: List<Color> = listOf(
    Color(0xFFEF4094),
    Color(0xFF8D02FF),
)

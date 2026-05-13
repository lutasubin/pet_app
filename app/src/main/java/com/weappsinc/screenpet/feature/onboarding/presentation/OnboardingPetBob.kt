package com.weappsinc.screenpet.feature.onboarding.presentation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import kotlin.math.PI
import kotlin.math.sin

/**
 * Modifier tao chuyen dong "tho" nhe theo truc Y cho pet PNG tren onboarding.
 * Khong dung Animatable spring/spec phuc tap: chi infiniteTransition + sin de tiet kiem.
 *
 * - [phaseOffsetMs]: lech pha so voi pet khac (de tranh dong loat).
 */
@Composable
fun Modifier.idleBob(
    phaseOffsetMs: Int = 0,
    periodMs: Int = OnboardingTokens.PetBobPeriodMs,
): Modifier {
    val transition = rememberInfiniteTransition(label = "onboardingIdleBob")
    val t by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = periodMs, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "bobT",
    )
    val phase = (phaseOffsetMs.toFloat() / periodMs.toFloat()).coerceIn(0f, 1f)
    val amplitudePx = with(LocalDensity.current) { OnboardingTokens.PetBobAmplitudeDp.toPx() }
    val offset = sin(((t + phase) * 2f * PI).toFloat()) * amplitudePx
    return this.then(Modifier.graphicsLayer { translationY = offset })
}

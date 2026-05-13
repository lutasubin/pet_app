package com.weappsinc.screenpet.feature.settings.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

/**
 * Overlay full-man trang dac + spinner gradient.
 *
 * Dung [MutableTransitionState] de neu gate active NGAY tu lan composition dau (vi du
 * sau khi recompose do config change), overlay xuat hien LUC opacity = 1 → khong co
 * fade-in 120ms gay lo content phia sau.
 */
@Composable
fun LocaleTransitionOverlay(modifier: Modifier = Modifier) {
    val active by rememberAppLocaleTransitionActive()
    val visibleState = remember { MutableTransitionState(active) }
    LaunchedEffect(active) { visibleState.targetState = active }

    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(tween(120)),
        exit = fadeOut(tween(220)),
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .pointerInput(Unit) { /* nuot moi cham khi dang loading */ },
            contentAlignment = Alignment.Center,
        ) {
            GradientCircularLoading()
        }
    }
}

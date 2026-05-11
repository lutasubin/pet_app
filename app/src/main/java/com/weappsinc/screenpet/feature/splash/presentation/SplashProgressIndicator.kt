package com.weappsinc.screenpet.feature.splash.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.ui.theme.SplashTokens

@Composable
fun SplashProgressIndicator(progress: Float, modifier: Modifier = Modifier) {
    BoxWithConstraints(
        modifier = modifier
            .height(10.dp)
            .clip(RoundedCornerShape(50))
            .background(SplashTokens.ProgressTrack),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .clip(RoundedCornerShape(50))
                .background(SplashTokens.ProgressFill),
        )
    }
}

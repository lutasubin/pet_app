package com.weappsinc.screenpet.feature.splash.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import com.weappsinc.screenpet.ui.theme.SplashTokens

@Composable
fun SplashTitleText(text: String, modifier: Modifier = Modifier) {
    val base = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
    val strokePx = 5f
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(
            text = text,
            color = SplashTokens.TitleStroke,
            style = base.copy(drawStyle = Stroke(width = strokePx)),
            maxLines = 1,
        )
        Text(
            text = text,
            style = base.copy(
                brush = Brush.verticalGradient(
                    listOf(SplashTokens.TitleGradientTop, SplashTokens.TitleGradientBottom),
                ),
                drawStyle = Fill,
            ),
            maxLines = 1,
        )
    }
}

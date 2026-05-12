package com.weappsinc.screenpet.feature.home.presentation.nav

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

/**
 * Nền tùy chỉnh cho màn xem trước pet: gradient dịu, sao, vệt sáng — không dùng ảnh PNG xấu.
 */
@Composable
fun ShopPetPreviewBackdrop(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0f to Color(0xFF12101C),
                    0.35f to Color(0xFF1E1635),
                    0.62f to Color(0xFF352456),
                    0.82f to Color(0xFF4A2F62),
                    1f to Color(0xFF2A1A3D),
                ),
            ),
    ) {
        Canvas(Modifier.fillMaxSize()) {
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0x30181028), Color.Transparent, Color(0x18000000)),
                    startY = 0f,
                    endY = size.height,
                ),
            )
            val rnd = Random(7)
            repeat(90) {
                val a = rnd.nextFloat() * 0.35f + 0.12f
                drawCircle(
                    color = Color(0xFFE8D4FF).copy(alpha = a),
                    radius = rnd.nextFloat() * 1.2f + 0.35f,
                    center = Offset(rnd.nextFloat() * size.width, rnd.nextFloat() * size.height),
                )
            }
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x55964AD4), Color.Transparent),
                    center = Offset(size.width * 0.2f, size.height * 0.28f),
                    radius = size.minDimension * 0.62f,
                ),
                radius = size.minDimension * 0.62f,
                center = Offset(size.width * 0.2f, size.height * 0.28f),
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x45EE4096), Color.Transparent),
                    center = Offset(size.width * 0.88f, size.height * 0.42f),
                    radius = size.minDimension * 0.45f,
                ),
                radius = size.minDimension * 0.45f,
                center = Offset(size.width * 0.88f, size.height * 0.42f),
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x38B39DDB), Color.Transparent),
                    center = Offset(size.width * 0.55f, size.height * 0.78f),
                    radius = size.minDimension * 0.5f,
                ),
                radius = size.minDimension * 0.5f,
                center = Offset(size.width * 0.55f, size.height * 0.78f),
            )
        }
    }
}

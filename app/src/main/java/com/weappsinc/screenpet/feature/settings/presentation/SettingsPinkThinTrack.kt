package com.weappsinc.screenpet.feature.settings.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.util.lerp
import com.weappsinc.screenpet.ui.theme.HomeTokens

/**
 * Track slider mong, hai dau tron (StrokeCap.Round), khong ve tick — giong mockup Size/Speed.
 * Dung Canvas thay vi SliderDefaults.Track vi track M3 luon ep chieu cao noi bo.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPinkThinTrack(
    sliderState: SliderState,
    activeColor: Color,
    inactiveColor: Color,
    modifier: Modifier = Modifier,
) {
    val fraction = sliderState.coercedValueAsFraction
    val layoutDir = LocalLayoutDirection.current
    Canvas(
        modifier
            .fillMaxWidth()
            .height(HomeTokens.SettingsSliderTrackLayoutHeightDp),
    ) {
        val cy = size.height / 2f
        val sw = HomeTokens.SettingsSliderTrackStrokeDp.toPx().coerceAtLeast(1.5f)
        val xLeft = 0f
        val xRight = size.width
        val xStart = if (layoutDir == LayoutDirection.Rtl) xRight else xLeft
        val xEnd = if (layoutDir == LayoutDirection.Rtl) xLeft else xRight
        val xThumb = lerp(xStart, xEnd, fraction)
        drawLine(
            color = inactiveColor,
            start = Offset(xStart, cy),
            end = Offset(xEnd, cy),
            strokeWidth = sw,
            cap = StrokeCap.Round,
        )
        drawLine(
            color = activeColor,
            start = Offset(xStart, cy),
            end = Offset(xThumb, cy),
            strokeWidth = sw,
            cap = StrokeCap.Round,
        )
    }
}

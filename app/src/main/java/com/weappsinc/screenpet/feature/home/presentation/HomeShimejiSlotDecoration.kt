package com.weappsinc.screenpet.feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.ui.theme.HomeTokens

private val SLOT_CORNER_DP = 20.dp

internal val HomeShimejiSlotShape = RoundedCornerShape(SLOT_CORNER_DP)

/** Bề mặt ô: khoá = card lavender + đổ bóng; trống / đã chọn = trắng + viền nét đứt. */
internal fun Modifier.homeShimejiSlotSurface(model: HomeSlotUiModel): Modifier = when (model) {
    is HomeSlotUiModel.CharacterLocked,
    is HomeSlotUiModel.SlotLocked,
    ->
        shadow(
            elevation = HomeTokens.SlotUnlockCardElevationDp,
            shape = HomeShimejiSlotShape,
            clip = false,
            ambientColor = HomeTokens.SlotUnlockCardShadowAmbient,
            spotColor = HomeTokens.SlotUnlockCardShadowSpot,
        ).background(HomeTokens.SlotUnlockCardSurface, HomeShimejiSlotShape)
    HomeSlotUiModel.Empty,
    is HomeSlotUiModel.Picked,
    ->
        clip(HomeShimejiSlotShape).drawWhiteDashedSlot()
}

private fun Modifier.drawWhiteDashedSlot(): Modifier = drawBehind {
    val r = SLOT_CORNER_DP.toPx()
    val corner = CornerRadius(r, r)
    drawRoundRect(color = Color.White, cornerRadius = corner)
    drawRoundRect(
        color = HomeTokens.SlotEmptyDashBorder,
        style = Stroke(
            width = 1.8.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f), 0f),
        ),
        cornerRadius = corner,
    )
}

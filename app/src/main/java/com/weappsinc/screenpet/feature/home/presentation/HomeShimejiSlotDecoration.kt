package com.weappsinc.screenpet.feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.ui.theme.HomeTokens

private val SLOT_CORNER_DP = 20.dp

internal val HomeShimejiSlotShape = RoundedCornerShape(SLOT_CORNER_DP)

/** Nen + vien slot theo loai (Empty / Picked / khoa). */
internal fun Modifier.homeShimejiSlotDecoration(model: HomeSlotUiModel): Modifier = when (model) {
    HomeSlotUiModel.Empty -> drawEmptySelectableSlot()
    is HomeSlotUiModel.Picked -> background(Color.White, HomeShimejiSlotShape)
    is HomeSlotUiModel.CharacterLocked,
    is HomeSlotUiModel.SlotLocked,
    -> drawLockedSlot()
}

private fun Modifier.drawEmptySelectableSlot(): Modifier = drawBehind {
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

private fun Modifier.drawLockedSlot(): Modifier = drawBehind {
    val r = SLOT_CORNER_DP.toPx()
    val corner = CornerRadius(r, r)
    drawRoundRect(color = Color.White, cornerRadius = corner)
    drawRoundRect(
        color = HomeTokens.SlotLockedBorder,
        style = Stroke(width = 1.dp.toPx()),
        cornerRadius = corner,
    )
}

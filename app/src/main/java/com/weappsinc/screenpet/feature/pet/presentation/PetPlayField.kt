package com.weappsinc.screenpet.feature.pet.presentation

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import com.weappsinc.screenpet.core.constants.PetAssetPaths
import com.weappsinc.screenpet.core.constants.PetSpriteAnchor
import com.weappsinc.screenpet.core.constants.ShimejiFrameCatalog
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState
import kotlin.math.roundToInt

@Composable
fun PetPlayField(
    world: PetWorldState,
    eventSink: PetPlayEventSink,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    PetSpritePrefetcher()
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        LaunchedEffect(maxWidth, maxHeight) {
            val wPx = with(density) { maxWidth.roundToPx() }
            val hPx = with(density) { maxHeight.roundToPx() }
            eventSink.onPlayAreaSized(wPx, hPx)
        }
        val snap = world.snapshot
        val clip = ShimejiFrameCatalog.clip(snap.clipId)
        val idx = snap.frameIndex.coerceIn(0, clip.frameIndices.lastIndex)
        val path = PetAssetPaths.shimeRelativePath(clip.frameIndices[idx])
        val leftPx = snap.anchorXPx - PetSpriteAnchor.ANCHOR_X_IN_SPRITE
        val topPx = snap.anchorYPx - PetSpriteAnchor.ANCHOR_Y_IN_SPRITE
        val snapState = rememberUpdatedState(snap)
        val velocityTracker = remember { PetDragVelocityTracker() }
        PetShimeSprite(
            assetRelativePath = path,
            flipHorizontal = snap.lookRight,
            modifier = Modifier
                .offset { IntOffset(leftPx.roundToInt(), topPx.roundToInt()) }
                .pointerInput(Unit) {
                    var startX = 0f
                    var startY = 0f
                    var totalDx = 0f
                    var totalDy = 0f
                    detectDragGestures(
                        onDragStart = {
                            startX = snapState.value.anchorXPx
                            startY = snapState.value.anchorYPx
                            totalDx = 0f
                            totalDy = 0f
                            velocityTracker.reset(startX, startY)
                            eventSink.onPointerDown(startX, startY)
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            totalDx += dragAmount.x
                            totalDy += dragAmount.y
                            val ax = startX + totalDx
                            val ay = startY + totalDy
                            velocityTracker.addSample(ax, ay)
                            eventSink.onPointerMove(ax, ay)
                        },
                        onDragEnd = {
                            val (vx, vy) = velocityTracker.velocityPxPerSec()
                            eventSink.onPointerUp(vx, vy)
                        },
                        onDragCancel = {
                            val (vx, vy) = velocityTracker.velocityPxPerSec()
                            eventSink.onPointerUp(vx, vy)
                        },
                    )
                },
        )
    }
}

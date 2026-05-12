package com.weappsinc.screenpet.feature.pet.presentation.overlay

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weappsinc.screenpet.core.constants.PetAssetPaths
import com.weappsinc.screenpet.core.constants.PetSpriteAnchor
import com.weappsinc.screenpet.core.constants.ShimejiFrameCatalog
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetId
import com.weappsinc.screenpet.feature.pet.domain.repository.PetArenaRepository
import com.weappsinc.screenpet.feature.pet.presentation.PetDragVelocityTracker
import com.weappsinc.screenpet.feature.pet.presentation.PetPlayEventSink
import com.weappsinc.screenpet.feature.pet.presentation.PetShimeSprite
import com.weappsinc.screenpet.ui.theme.App_petTheme
import kotlin.math.roundToInt

@Composable
fun PetArenaOverlaySpriteHost(
    repository: PetArenaRepository,
    petId: PetId,
    eventSink: PetPlayEventSink,
    displayScale: Float,
) {
    val arena by repository.arena.collectAsStateWithLifecycle()
    val entity = arena.pets[petId] ?: return
    val density = LocalDensity.current
    val sizePx = PetSpriteAnchor.SPRITE_WIDTH_PX * displayScale
    val sizeDp = with(density) { sizePx.toDp() }
    val snapState = rememberUpdatedState(entity.snapshot)
    val velocityTracker = remember { PetDragVelocityTracker() }
    val clip = ShimejiFrameCatalog.clip(entity.snapshot.clipId)
    val idx = entity.snapshot.frameIndex.coerceIn(0, clip.frameIndices.lastIndex)
    val path = PetAssetPaths.shimeRelativePath(entity.assetFolder, clip.frameIndices[idx])
    App_petTheme {
        Box(
            modifier = Modifier
                .size(sizeDp, sizeDp)
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
        ) {
            PetShimeSprite(
                assetRelativePath = path,
                flipHorizontal = entity.snapshot.lookRight,
                sizePxOverride = sizePx.roundToInt().takeIf { it != PetSpriteAnchor.SPRITE_WIDTH_PX },
            )
        }
    }
}

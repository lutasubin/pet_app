package com.weappsinc.screenpet.feature.pet.presentation.overlay

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weappsinc.screenpet.core.constants.PetAssetPaths
import com.weappsinc.screenpet.core.constants.PetSpriteAnchor
import com.weappsinc.screenpet.core.constants.ShimejiFrameCatalog
import com.weappsinc.screenpet.feature.pet.domain.repository.PetSimulationRepository
import com.weappsinc.screenpet.feature.pet.presentation.PetPlayEventSink
import com.weappsinc.screenpet.feature.pet.presentation.PetShimeSprite
import com.weappsinc.screenpet.feature.pet.presentation.PetSpritePrefetcher
import com.weappsinc.screenpet.ui.theme.App_petTheme

@Composable
fun PetOverlaySpriteHost(
    repository: PetSimulationRepository,
    eventSink: PetPlayEventSink,
) {
    val world by repository.world.collectAsStateWithLifecycle()
    val density = LocalDensity.current
    val sizePx = PetSpriteAnchor.SPRITE_WIDTH_PX * PetSpriteAnchor.OVERLAY_DISPLAY_SCALE
    val sizeDp = with(density) { sizePx.toDp() }
    val snapState = rememberUpdatedState(world.snapshot)
    val clip = ShimejiFrameCatalog.clip(world.snapshot.clipId)
    val idx = world.snapshot.frameIndex.coerceIn(0, clip.frameIndices.lastIndex)
    val path = PetAssetPaths.shimeRelativePath(clip.frameIndices[idx])
    App_petTheme {
        PetSpritePrefetcher()
        Box(
            modifier = Modifier
                .size(sizeDp, sizeDp)
                .background(Color(0x22FF0000))
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
                            eventSink.onPointerDown(startX, startY)
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            totalDx += dragAmount.x
                            totalDy += dragAmount.y
                            eventSink.onPointerMove(startX + totalDx, startY + totalDy)
                        },
                        onDragEnd = { eventSink.onPointerUp() },
                        onDragCancel = { eventSink.onPointerUp() },
                    )
                },
        ) {
            PetShimeSprite(
                assetRelativePath = path,
                flipHorizontal = !world.snapshot.lookRight,
                sizePxOverride = sizePx,
            )
        }
    }
}

package com.weappsinc.screenpet.feature.pet.domain.engine

import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.model.PetEngineErrorKind
import com.weappsinc.screenpet.feature.pet.domain.model.PetEngineException
import com.weappsinc.screenpet.feature.pet.domain.model.PetInput
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.model.PetRuntimePhase
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState

/**
 * Kernel mo phong cho mot pet (mot [PetWorldState]).
 * Nhieu pet: dung [com.weappsinc.screenpet.feature.pet.domain.petsim.PetArenaSimulator] + [PetArenaState].
 */
object PetEngine {
    private const val MIN_PLAY_SIZE_PX: Int = 128

    fun advance(world: PetWorldState, input: PetInput): Result<PetWorldState> = runCatching {
        when (input) {
            is PetInput.Layout -> handleLayout(world, input.playArea)
            is PetInput.PointerDown -> handlePointerDown(world, input.xPx, input.yPx)
            is PetInput.PointerMove -> handlePointerMove(world, input.xPx, input.yPx)
            is PetInput.PointerUp ->
                PetEnginePointerRelease.onPointerUp(
                    world,
                    input.releaseVelocityXPxPerSec,
                    input.releaseVelocityYPxPerSec,
                )
            is PetInput.Tick -> PetEnginePhysics.onTick(world, input.dtMs)
        }
    }

    private fun handleLayout(world: PetWorldState, area: PetPlayArea): PetWorldState {
        if (area.widthPx < MIN_PLAY_SIZE_PX || area.heightPx < MIN_PLAY_SIZE_PX) {
            throw PetEngineException(PetEngineErrorKind.PlayAreaTooSmall)
        }
        val s = PetBoundsGeometry.clampAnchor(world.snapshot, area)
        return PetWorldState(
            playArea = area,
            snapshot = s.copy(contact = PetBoundsGeometry.computeContact(s, area)),
        )
    }

    private fun handlePointerDown(world: PetWorldState, x: Float, y: Float): PetWorldState {
        val s = world.snapshot
        if (s.phase == PetRuntimePhase.PreviewHold) return world
        if (!PetBoundsGeometry.hitTestPointer(x, y, s)) return world
        return world.copy(
            snapshot = s.copy(
                isDragging = true,
                phase = PetRuntimePhase.Dragged,
                dragGrabOffsetXPx = s.anchorXPx - x,
                dragGrabOffsetYPx = s.anchorYPx - y,
                clipId = ShimejiClipId.PinchDragPreview,
                velocityXPxPerSec = 0f,
                velocityYPxPerSec = 0f,
                frameIndex = 0,
                msAccumulatedInFrame = 0f,
            ),
            dragAnchorStartXPx = s.anchorXPx,
            dragAnchorStartYPx = s.anchorYPx,
        )
    }

    private fun handlePointerMove(world: PetWorldState, x: Float, y: Float): PetWorldState {
        if (world.snapshot.phase == PetRuntimePhase.PreviewHold) return world
        if (!world.snapshot.isDragging) return world
        val s = world.snapshot
        val moved = s.copy(
            anchorXPx = x + s.dragGrabOffsetXPx,
            anchorYPx = y + s.dragGrabOffsetYPx,
        )
        val clamped = PetBoundsGeometry.clampAnchor(moved, world.playArea)
        return world.copy(
            snapshot = clamped.copy(contact = PetBoundsGeometry.computeContact(clamped, world.playArea)),
        )
    }
}

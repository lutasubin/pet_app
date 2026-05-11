package com.weappsinc.screenpet.feature.pet.domain.engine

import com.weappsinc.screenpet.core.constants.PetGestureConstants
import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.model.PetRuntimePhase
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState
import kotlin.math.hypot

/** Tha tay: ngoi san, bam tran, hoac nem theo vi tri + van toc (px/s). */
internal object PetEnginePointerRelease {
    fun onPointerUp(world: PetWorldState, vx: Float, vy: Float): PetWorldState {
        if (world.snapshot.phase == PetRuntimePhase.PreviewHold) return world
        if (!world.snapshot.isDragging) return world
        PetEngineTopEdgeWallDragRelease.tryStart(world)?.let { return it }
        val s = world.snapshot
        val area = world.playArea
        val maxY = PetBoundsGeometry.maxAnchorY(area)
        val minY = PetBoundsGeometry.minAnchorY(area)
        val speed = hypot(vx, vy)
        val scaledVx = vx * PetGestureConstants.RELEASE_VELOCITY_SCALE
        val scaledVy = vy * PetGestureConstants.RELEASE_VELOCITY_SCALE
        val nearTop = s.anchorYPx <= minY + PetGestureConstants.CEILING_PROXIMITY_PX
        val nearFloor = s.anchorYPx >= maxY - PetGestureConstants.FLOOR_PROXIMITY_PX

        if (nearTop && speed < PetGestureConstants.REST_SPEED_PX_PER_SEC) {
            val ns = PetBoundsGeometry.clampAnchor(
                s.copy(
                    isDragging = false,
                    phase = PetRuntimePhase.GroundIdle,
                    clipId = ShimejiClipId.SitLookUp,
                    velocityXPxPerSec = 0f,
                    velocityYPxPerSec = 0f,
                    anchorYPx = minY,
                    frameIndex = 0,
                    msAccumulatedInFrame = 0f,
                ),
                area,
            )
            return world.clearedDragAnchors().copy(
                snapshot = ns.copy(contact = PetBoundsGeometry.computeContact(ns, area)),
            )
        }

        if (nearFloor && speed < PetGestureConstants.REST_SPEED_PX_PER_SEC) {
            val sitClip = if (vy > PetGestureConstants.SIT_DANGLE_DOWN_VY_PX_PER_SEC) {
                ShimejiClipId.SitDangleLegs
            } else {
                ShimejiClipId.Sit
            }
            val ns = PetBoundsGeometry.clampAnchor(
                s.copy(
                    isDragging = false,
                    phase = PetRuntimePhase.GroundIdle,
                    clipId = sitClip,
                    velocityXPxPerSec = 0f,
                    velocityYPxPerSec = 0f,
                    anchorYPx = maxY,
                    frameIndex = 0,
                    msAccumulatedInFrame = 0f,
                ),
                area,
            )
            return world.clearedDragAnchors().copy(
                snapshot = ns.copy(contact = PetBoundsGeometry.computeContact(ns, area)),
            )
        }

        val strongUp = scaledVy < PetGestureConstants.FLING_UP_VY_PX_PER_SEC ||
            (
                scaledVy < PetGestureConstants.FLING_COMBO_VY_PX_PER_SEC &&
                    hypot(scaledVx, scaledVy) >= PetGestureConstants.FLING_COMBO_SPEED_PX_PER_SEC
                )
        val outVy = if (strongUp) scaledVy.coerceAtMost(-320f) else scaledVy
        return world.clearedDragAnchors().copy(
            snapshot = s.copy(
                isDragging = false,
                phase = PetRuntimePhase.Airborne,
                clipId = ShimejiClipId.Falling,
                velocityXPxPerSec = scaledVx,
                velocityYPxPerSec = outVy,
                frameIndex = 0,
                msAccumulatedInFrame = 0f,
            ),
        )
    }
}

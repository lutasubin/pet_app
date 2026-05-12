package com.weappsinc.screenpet.feature.pet.domain.engine

import com.weappsinc.screenpet.core.constants.PetGestureConstants
import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.model.PerimeterPatrolStage
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.model.PetRuntimePhase
import com.weappsinc.screenpet.feature.pet.domain.model.PetSnapshot
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState

/**
 * Keo tu vung mép trên, tha gan mép trai/phai -> bat dau bò tuong xuong san.
 */
internal object PetEngineTopEdgeWallDragRelease {
    fun tryStart(world: PetWorldState): PetWorldState? {
        if (world.dragAnchorStartXPx == null) return null
        val startY = world.dragAnchorStartYPx ?: return null
        val s = world.snapshot
        val area = world.playArea
        val minY = PetBoundsGeometry.minAnchorY(area)
        if (startY > minY + PetGestureConstants.CEILING_PROXIMITY_PX) return null

        val minX = PetBoundsGeometry.minAnchorX(area)
        val maxX = PetBoundsGeometry.maxAnchorX(area)
        val pad = PetGestureConstants.TOP_TO_WALL_EDGE_RELEASE_PX

        if (s.anchorXPx <= minX + pad) {
            val ns = wallDescendSnapshot(s, area, PetRuntimePhase.WallLeft, minX)
            return world.clearedDragAnchors().copy(snapshot = ns)
        }
        if (s.anchorXPx >= maxX - pad) {
            val ns = wallDescendSnapshot(s, area, PetRuntimePhase.WallRight, maxX)
            return world.clearedDragAnchors().copy(snapshot = ns)
        }
        return null
    }

    private fun wallDescendSnapshot(
        s: PetSnapshot,
        area: PetPlayArea,
        phase: PetRuntimePhase,
        anchorX: Float,
    ): PetSnapshot {
        val clamped = PetBoundsGeometry.clampAnchor(
            s.copy(
                isDragging = false,
                phase = phase,
                clipId = ShimejiClipId.ClimbWall,
                wallDescend = true,
                anchorXPx = anchorX,
                velocityXPxPerSec = 0f,
                velocityYPxPerSec = 0f,
                frameIndex = 0,
                msAccumulatedInFrame = 0f,
                perimeterStage = if (s.perimeterPatrolEnabled) {
                    PerimeterPatrolStage.DescendFirstThird
                } else {
                    s.perimeterStage
                },
            ),
            area,
        )
        return clamped.copy(contact = PetBoundsGeometry.computeContact(clamped, area))
    }
}

package com.weappsinc.screenpet.feature.pet.domain.engine

import com.weappsinc.screenpet.core.constants.PetPhysicsConstants
import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.model.PerimeterPatrolStage
import com.weappsinc.screenpet.feature.pet.domain.model.PetRuntimePhase
import com.weappsinc.screenpet.feature.pet.domain.model.PetSnapshot
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState

internal object PetEngineAirPhysics {
    fun integrateAirborne(s: PetSnapshot, dt: Float): PetSnapshot {
        val vy = s.velocityYPxPerSec + PetPhysicsConstants.GRAVITY_PX_PER_SEC2 * dt
        val vx = s.velocityXPxPerSec
        val look = when {
            vx > 40f -> true
            vx < -40f -> false
            else -> s.lookRight
        }
        return s.copy(
            velocityYPxPerSec = vy,
            velocityXPxPerSec = vx,
            anchorXPx = s.anchorXPx + vx * dt,
            anchorYPx = s.anchorYPx + vy * dt,
            clipId = ShimejiClipId.Falling,
            lookRight = look,
        )
    }

    fun resolveLanding(s: PetSnapshot, world: PetWorldState, _dtMs: Long): PetSnapshot {
        if (s.phase != PetRuntimePhase.Airborne) return s
        val maxY = PetBoundsGeometry.maxAnchorY(world.playArea)
        if (s.anchorYPx >= maxY - 1f && s.velocityYPxPerSec >= 0f) {
            if (s.perimeterPatrolEnabled) {
                when (s.perimeterStage) {
                    PerimeterPatrolStage.AirCrossDescendAfterFirstThird -> {
                        val toLeft = s.velocityXPxPerSec < 0f
                        val minX = PetBoundsGeometry.minAnchorX(world.playArea)
                        val maxX = PetBoundsGeometry.maxAnchorX(world.playArea)
                        val rec = if (toLeft) {
                            s.copy(
                                anchorYPx = maxY,
                                anchorXPx = minX,
                                phase = PetRuntimePhase.WallLeft,
                                clipId = ShimejiClipId.GrabWall,
                                velocityXPxPerSec = 0f,
                                velocityYPxPerSec = 0f,
                                wallDescend = true,
                                perimeterStage = PerimeterPatrolStage.DescendSecondThird,
                                frameIndex = 0,
                                msAccumulatedInFrame = 0f,
                            )
                        } else {
                            s.copy(
                                anchorYPx = maxY,
                                anchorXPx = maxX,
                                phase = PetRuntimePhase.WallRight,
                                clipId = ShimejiClipId.GrabWall,
                                velocityXPxPerSec = 0f,
                                velocityYPxPerSec = 0f,
                                wallDescend = true,
                                perimeterStage = PerimeterPatrolStage.DescendSecondThird,
                                frameIndex = 0,
                                msAccumulatedInFrame = 0f,
                            )
                        }
                        return PetBoundsGeometry.clampAnchor(rec, world.playArea)
                    }
                    PerimeterPatrolStage.AirCrossDescendAfterSecondThird -> {
                        val toLeft = s.velocityXPxPerSec < 0f
                        val minX = PetBoundsGeometry.minAnchorX(world.playArea)
                        val maxX = PetBoundsGeometry.maxAnchorX(world.playArea)
                        val rec = if (toLeft) {
                            s.copy(
                                anchorYPx = maxY,
                                anchorXPx = minX,
                                phase = PetRuntimePhase.WallLeft,
                                clipId = ShimejiClipId.GrabWall,
                                velocityXPxPerSec = 0f,
                                velocityYPxPerSec = 0f,
                                wallDescend = true,
                                perimeterStage = PerimeterPatrolStage.DescendToFloor,
                                frameIndex = 0,
                                msAccumulatedInFrame = 0f,
                            )
                        } else {
                            s.copy(
                                anchorYPx = maxY,
                                anchorXPx = maxX,
                                phase = PetRuntimePhase.WallRight,
                                clipId = ShimejiClipId.GrabWall,
                                velocityXPxPerSec = 0f,
                                velocityYPxPerSec = 0f,
                                wallDescend = true,
                                perimeterStage = PerimeterPatrolStage.DescendToFloor,
                                frameIndex = 0,
                                msAccumulatedInFrame = 0f,
                            )
                        }
                        return PetBoundsGeometry.clampAnchor(rec, world.playArea)
                    }
                    PerimeterPatrolStage.AirCrossAfterFirstThird -> {
                        val toLeft = s.velocityXPxPerSec < 0f
                        val minX = PetBoundsGeometry.minAnchorX(world.playArea)
                        val maxX = PetBoundsGeometry.maxAnchorX(world.playArea)
                        val rec = if (toLeft) {
                            s.copy(
                                anchorYPx = maxY,
                                anchorXPx = minX,
                                phase = PetRuntimePhase.WallLeft,
                                clipId = ShimejiClipId.GrabWall,
                                velocityXPxPerSec = 0f,
                                velocityYPxPerSec = 0f,
                                perimeterStage = PerimeterPatrolStage.AscendSecondThird,
                                frameIndex = 0,
                                msAccumulatedInFrame = 0f,
                            )
                        } else {
                            s.copy(
                                anchorYPx = maxY,
                                anchorXPx = maxX,
                                phase = PetRuntimePhase.WallRight,
                                clipId = ShimejiClipId.GrabWall,
                                velocityXPxPerSec = 0f,
                                velocityYPxPerSec = 0f,
                                perimeterStage = PerimeterPatrolStage.AscendSecondThird,
                                frameIndex = 0,
                                msAccumulatedInFrame = 0f,
                            )
                        }
                        return PetBoundsGeometry.clampAnchor(rec, world.playArea)
                    }
                    PerimeterPatrolStage.AirCrossAfterSecondThird -> {
                        val toLeft = s.velocityXPxPerSec < 0f
                        val minX = PetBoundsGeometry.minAnchorX(world.playArea)
                        val maxX = PetBoundsGeometry.maxAnchorX(world.playArea)
                        val rec = if (toLeft) {
                            s.copy(
                                anchorYPx = maxY,
                                anchorXPx = minX,
                                phase = PetRuntimePhase.WallLeft,
                                clipId = ShimejiClipId.GrabWall,
                                velocityXPxPerSec = 0f,
                                velocityYPxPerSec = 0f,
                                perimeterStage = PerimeterPatrolStage.AscendToTop,
                                frameIndex = 0,
                                msAccumulatedInFrame = 0f,
                            )
                        } else {
                            s.copy(
                                anchorYPx = maxY,
                                anchorXPx = maxX,
                                phase = PetRuntimePhase.WallRight,
                                clipId = ShimejiClipId.GrabWall,
                                velocityXPxPerSec = 0f,
                                velocityYPxPerSec = 0f,
                                perimeterStage = PerimeterPatrolStage.AscendToTop,
                                frameIndex = 0,
                                msAccumulatedInFrame = 0f,
                            )
                        }
                        return PetBoundsGeometry.clampAnchor(rec, world.playArea)
                    }
                    else -> { }
                }
            }
            return s.copy(
                anchorYPx = maxY,
                velocityYPxPerSec = 0f,
                velocityXPxPerSec = s.velocityXPxPerSec * 0.35f,
                phase = PetRuntimePhase.Bouncing,
                clipId = ShimejiClipId.Bouncing,
                frameIndex = 0,
                msAccumulatedInFrame = 0f,
                bouncePhaseRemainingMs = PetPhysicsConstants.BOUNCE_PHASE_MS,
            )
        }
        return s
    }
}

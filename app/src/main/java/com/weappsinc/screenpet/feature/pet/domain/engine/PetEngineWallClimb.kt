package com.weappsinc.screenpet.feature.pet.domain.engine

import com.weappsinc.screenpet.core.constants.PetPhysicsConstants
import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.model.PetRuntimePhase
import com.weappsinc.screenpet.feature.pet.domain.model.PetSnapshot
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState

/** Leo tuong len tran hoac bò xuong san (wallDescend). */
internal object PetEngineWallClimb {
    private const val CLIMB_UP_PX_PER_SEC: Float = -220f
    private const val CLIMB_DOWN_PX_PER_SEC: Float = 220f

    fun tick(s: PetSnapshot, world: PetWorldState, dt: Float): PetSnapshot {
        val maxY = PetBoundsGeometry.maxAnchorY(world.playArea)
        val minY = PetBoundsGeometry.minAnchorY(world.playArea)
        val climbVy = if (s.wallDescend) CLIMB_DOWN_PX_PER_SEC else CLIMB_UP_PX_PER_SEC
        var ns = s.copy(
            clipId = ShimejiClipId.ClimbWall,
            velocityXPxPerSec = 0f,
            velocityYPxPerSec = climbVy,
            anchorYPx = s.anchorYPx + climbVy * dt,
        )
        ns = PetBoundsGeometry.clampAnchor(ns, world.playArea)
        val c = PetBoundsGeometry.computeContact(ns, world.playArea)

        if (!s.wallDescend && c.ceiling) {
            return PetBoundsGeometry.clampAnchor(
                ns.copy(
                    phase = PetRuntimePhase.GroundIdle,
                    clipId = ShimejiClipId.SitLookUp,
                    velocityXPxPerSec = 0f,
                    velocityYPxPerSec = 0f,
                    anchorYPx = minY,
                    wallDescend = false,
                    frameIndex = 0,
                    msAccumulatedInFrame = 0f,
                ),
                world.playArea,
            )
        }

        if (s.wallDescend && (c.floor || ns.anchorYPx >= maxY - 1f)) {
            val walkRight = s.phase == PetRuntimePhase.WallLeft
            val vx = if (walkRight) {
                PetPhysicsConstants.WALK_SPEED_PX_PER_SEC
            } else {
                -PetPhysicsConstants.WALK_SPEED_PX_PER_SEC
            }
            return PetBoundsGeometry.clampAnchor(
                ns.copy(
                    phase = PetRuntimePhase.GroundMoving,
                    clipId = ShimejiClipId.Walk,
                    anchorYPx = maxY,
                    lookRight = walkRight,
                    velocityXPxPerSec = vx,
                    velocityYPxPerSec = 0f,
                    wallDescend = false,
                    frameIndex = 0,
                    msAccumulatedInFrame = 0f,
                ),
                world.playArea,
            )
        }

        if (!c.wallLeft && !c.wallRight) {
            return ns.copy(
                phase = PetRuntimePhase.Airborne,
                clipId = ShimejiClipId.Falling,
                velocityYPxPerSec = 120f,
                wallDescend = false,
            )
        }
        return ns
    }
}

package com.weappsinc.screenpet.feature.pet.domain.engine

import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.model.PetRuntimePhase
import com.weappsinc.screenpet.feature.pet.domain.model.PetSnapshot
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState

internal object PetEngineWallPhysics {
    fun tryStickWallFromAir(s: PetSnapshot, world: PetWorldState): PetSnapshot {
        if (s.phase != PetRuntimePhase.Airborne) return s
        val clamped = PetBoundsGeometry.clampAnchor(s, world.playArea)
        val c = PetBoundsGeometry.computeContact(clamped, world.playArea)
        if (c.wallLeft && s.velocityXPxPerSec < -40f) {
            return clamped.copy(
                phase = PetRuntimePhase.WallLeft,
                clipId = ShimejiClipId.GrabWall,
                velocityXPxPerSec = 0f,
                velocityYPxPerSec = 0f,
                anchorXPx = PetBoundsGeometry.minAnchorX(world.playArea),
                wallDescend = false,
            )
        }
        if (c.wallRight && s.velocityXPxPerSec > 40f) {
            return clamped.copy(
                phase = PetRuntimePhase.WallRight,
                clipId = ShimejiClipId.GrabWall,
                velocityXPxPerSec = 0f,
                velocityYPxPerSec = 0f,
                anchorXPx = PetBoundsGeometry.maxAnchorX(world.playArea),
                wallDescend = false,
            )
        }
        return s
    }

    fun wallClimbTick(s: PetSnapshot, world: PetWorldState, dt: Float): PetSnapshot =
        PetEngineWallClimb.tick(s, world, dt)

    fun ceilingTick(s: PetSnapshot, world: PetWorldState, dt: Float): PetSnapshot {
        val vx = if (s.lookRight) 90f else -90f
        var ns = s.copy(
            clipId = ShimejiClipId.ClimbCeiling,
            velocityXPxPerSec = vx,
            velocityYPxPerSec = 0f,
            anchorXPx = s.anchorXPx + vx * dt,
        )
        ns = PetBoundsGeometry.clampAnchor(ns, world.playArea)
        val c = PetBoundsGeometry.computeContact(ns, world.playArea)
        if (!c.ceiling && ns.anchorYPx > PetBoundsGeometry.minAnchorY(world.playArea) + 4f) {
            return ns.copy(
                phase = PetRuntimePhase.Airborne,
                clipId = ShimejiClipId.Falling,
                velocityYPxPerSec = 80f,
                wallDescend = false,
            )
        }
        return ns
    }
}

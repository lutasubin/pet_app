package com.weappsinc.screenpet.feature.pet.domain.engine

import com.weappsinc.screenpet.core.constants.PetPhysicsConstants
import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.model.PetRuntimePhase
import com.weappsinc.screenpet.feature.pet.domain.model.PetSnapshot
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState

/**
 * Vat ly + FSM don gian: san, trong luc, nay, di ngang, tuong/tran.
 */
internal object PetEnginePhysics {
    fun onTick(world: PetWorldState, dtMs: Long): PetWorldState {
        val dt = (dtMs.coerceAtLeast(0L)).toFloat() / 1000f
        var s = world.snapshot
        if (s.isDragging) {
            val played = PetClipPlayback.advance(s, dtMs)
            return world.copy(
                snapshot = PetBoundsGeometry.clampAnchor(played, world.playArea).let { c ->
                    c.copy(contact = PetBoundsGeometry.computeContact(c, world.playArea))
                },
            )
        }
        s = PetClipPlayback.advance(s, dtMs)
        s = when (s.phase) {
            PetRuntimePhase.GroundIdle -> groundIdle(s)
            PetRuntimePhase.GroundMoving -> groundMoving(s, world, dt)
            PetRuntimePhase.Airborne -> PetEngineAirPhysics.integrateAirborne(s, dt)
            PetRuntimePhase.Bouncing -> bouncing(s, dtMs)
            PetRuntimePhase.Dragged -> s
            PetRuntimePhase.WallLeft, PetRuntimePhase.WallRight ->
                PetEngineWallPhysics.wallClimbTick(s, world, dt)
            PetRuntimePhase.Ceiling -> PetEngineWallPhysics.ceilingTick(s, world, dt)
        }
        s = PetEngineWallPhysics.tryStickWallFromAir(s, world)
        s = PetEngineAirPhysics.resolveLanding(s, world, dtMs)
        s = PetBoundsGeometry.clampAnchor(s, world.playArea)
        return world.copy(snapshot = s.copy(contact = PetBoundsGeometry.computeContact(s, world.playArea)))
    }

    private fun groundIdle(s: PetSnapshot): PetSnapshot = s.copy(
        velocityXPxPerSec = 0f,
        velocityYPxPerSec = 0f,
        clipId = ShimejiClipId.Stand,
    )

    private fun groundMoving(s: PetSnapshot, world: PetWorldState, dt: Float): PetSnapshot {
        var ns = s.copy(clipId = ShimejiClipId.Walk)
        val vx = if (ns.lookRight) {
            PetPhysicsConstants.WALK_SPEED_PX_PER_SEC
        } else {
            -PetPhysicsConstants.WALK_SPEED_PX_PER_SEC
        }
        ns = ns.copy(
            velocityXPxPerSec = vx,
            velocityYPxPerSec = 0f,
            anchorXPx = ns.anchorXPx + vx * dt,
            anchorYPx = PetBoundsGeometry.maxAnchorY(world.playArea),
        )
        return reflectWallIfNeeded(ns, world)
    }

    private fun bouncing(s: PetSnapshot, dtMs: Long): PetSnapshot {
        var ns = s.copy(
            clipId = ShimejiClipId.Bouncing,
            velocityYPxPerSec = 0f,
            velocityXPxPerSec = 0f,
        )
        val left = (ns.bouncePhaseRemainingMs - dtMs).coerceAtLeast(0L)
        return if (left == 0L) {
            ns.copy(
                phase = PetRuntimePhase.GroundIdle,
                clipId = ShimejiClipId.Stand,
                bouncePhaseRemainingMs = 0L,
                frameIndex = 0,
                msAccumulatedInFrame = 0f,
            )
        } else {
            ns.copy(bouncePhaseRemainingMs = left)
        }
    }

    private fun reflectWallIfNeeded(s: PetSnapshot, world: PetWorldState): PetSnapshot {
        val c = PetBoundsGeometry.computeContact(PetBoundsGeometry.clampAnchor(s, world.playArea), world.playArea)
        var ns = s
        if (c.wallLeft && ns.velocityXPxPerSec < 0f) {
            ns = ns.copy(lookRight = true, velocityXPxPerSec = PetPhysicsConstants.WALK_SPEED_PX_PER_SEC)
        }
        if (c.wallRight && ns.velocityXPxPerSec > 0f) {
            ns = ns.copy(lookRight = false, velocityXPxPerSec = -PetPhysicsConstants.WALK_SPEED_PX_PER_SEC)
        }
        return ns
    }
}

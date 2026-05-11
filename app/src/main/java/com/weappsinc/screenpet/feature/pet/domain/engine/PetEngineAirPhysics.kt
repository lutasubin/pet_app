package com.weappsinc.screenpet.feature.pet.domain.engine

import com.weappsinc.screenpet.core.constants.PetPhysicsConstants
import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.model.PetRuntimePhase
import com.weappsinc.screenpet.feature.pet.domain.model.PetSnapshot
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState

internal object PetEngineAirPhysics {
    fun integrateAirborne(s: PetSnapshot, dt: Float): PetSnapshot {
        val vy = s.velocityYPxPerSec + PetPhysicsConstants.GRAVITY_PX_PER_SEC2 * dt
        val vx = s.velocityXPxPerSec
        return s.copy(
            velocityYPxPerSec = vy,
            velocityXPxPerSec = vx,
            anchorXPx = s.anchorXPx + vx * dt,
            anchorYPx = s.anchorYPx + vy * dt,
            clipId = ShimejiClipId.Falling,
        )
    }

    fun resolveLanding(s: PetSnapshot, world: PetWorldState, _dtMs: Long): PetSnapshot {
        if (s.phase != PetRuntimePhase.Airborne) return s
        val maxY = PetBoundsGeometry.maxAnchorY(world.playArea)
        if (s.anchorYPx >= maxY - 1f && s.velocityYPxPerSec >= 0f) {
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

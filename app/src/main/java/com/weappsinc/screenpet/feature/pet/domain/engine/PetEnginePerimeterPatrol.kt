package com.weappsinc.screenpet.feature.pet.domain.engine

import com.weappsinc.screenpet.core.constants.PetPhysicsConstants
import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.model.PerimeterPatrolStage
import com.weappsinc.screenpet.feature.pet.domain.model.PetRuntimePhase
import com.weappsinc.screenpet.feature.pet.domain.model.PetSnapshot
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState

/** Logic bam vien: goc day -> leo 1/3 -> nhay sang tuong doi dien -> lap -> tran -> xuong. */
internal object PetEnginePerimeterPatrol {
    private const val HOP_DURATION_SEC: Float = 0.48f

    fun attachWallFromFloorCorner(s: PetSnapshot, world: PetWorldState, wallIsRight: Boolean): PetSnapshot {
        val area = world.playArea
        val maxY = PetBoundsGeometry.maxAnchorY(area)
        val x = if (wallIsRight) PetBoundsGeometry.maxAnchorX(area) else PetBoundsGeometry.minAnchorX(area)
        return PetBoundsGeometry.clampAnchor(
            s.copy(
                phase = if (wallIsRight) PetRuntimePhase.WallRight else PetRuntimePhase.WallLeft,
                clipId = ShimejiClipId.GrabWall,
                anchorXPx = x,
                anchorYPx = maxY,
                velocityXPxPerSec = 0f,
                velocityYPxPerSec = 0f,
                perimeterStage = PerimeterPatrolStage.AscendFirstThird,
                wallDescend = false,
                frameIndex = 0,
                msAccumulatedInFrame = 0f,
            ),
            area,
        )
    }

    fun startCrossJump(s: PetSnapshot, world: PetWorldState, towardLeft: Boolean, nextStage: PerimeterPatrolStage): PetSnapshot {
        val wPx = world.playArea.widthPx.toFloat().coerceAtLeast(200f)
        val vxMag = (wPx / HOP_DURATION_SEC).coerceIn(900f, 3400f)
        val t = wPx / vxMag
        val vy0 = -0.5f * PetPhysicsConstants.GRAVITY_PX_PER_SEC2 * t
        val vx = if (towardLeft) -vxMag else vxMag
        return s.copy(
            phase = PetRuntimePhase.Airborne,
            clipId = ShimejiClipId.Falling,
            velocityXPxPerSec = vx,
            velocityYPxPerSec = vy0,
            perimeterStage = nextStage,
            frameIndex = 0,
            msAccumulatedInFrame = 0f,
            wallDescend = false,
        )
    }

    fun firstThirdTargetY(world: PetWorldState): Float {
        val maxY = PetBoundsGeometry.maxAnchorY(world.playArea)
        val minY = PetBoundsGeometry.minAnchorY(world.playArea)
        val h = (maxY - minY).coerceAtLeast(90f)
        return maxY - h / 3f
    }

    fun secondThirdTargetY(world: PetWorldState): Float {
        val maxY = PetBoundsGeometry.maxAnchorY(world.playArea)
        val minY = PetBoundsGeometry.minAnchorY(world.playArea)
        val h = (maxY - minY).coerceAtLeast(90f)
        return maxY - 2f * h / 3f
    }

    fun enterCeilingFromWall(s: PetSnapshot, world: PetWorldState): PetSnapshot {
        val minY = PetBoundsGeometry.minAnchorY(world.playArea)
        val walkRight = s.phase == PetRuntimePhase.WallLeft
        return PetBoundsGeometry.clampAnchor(
            s.copy(
                phase = PetRuntimePhase.Ceiling,
                clipId = ShimejiClipId.ClimbCeiling,
                anchorYPx = minY,
                velocityXPxPerSec = if (walkRight) 90f else -90f,
                velocityYPxPerSec = 0f,
                lookRight = walkRight,
                perimeterStage = PerimeterPatrolStage.CeilingWalk,
                wallDescend = false,
                frameIndex = 0,
                msAccumulatedInFrame = 0f,
            ),
            world.playArea,
        )
    }
}

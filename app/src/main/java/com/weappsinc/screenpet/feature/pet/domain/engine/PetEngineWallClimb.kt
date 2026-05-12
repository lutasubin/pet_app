package com.weappsinc.screenpet.feature.pet.domain.engine

import com.weappsinc.screenpet.core.constants.PetPhysicsConstants
import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.model.PerimeterPatrolStage
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

        if (s.perimeterPatrolEnabled && !s.wallDescend) {
            when (s.perimeterStage) {
                PerimeterPatrolStage.AscendFirstThird -> {
                    val y1 = PetEnginePerimeterPatrol.firstThirdTargetY(world)
                    if (ns.anchorYPx <= y1) {
                        val towardLeft = s.phase == PetRuntimePhase.WallRight
                        return PetEnginePerimeterPatrol.startCrossJump(
                            ns,
                            world,
                            towardLeft = towardLeft,
                            nextStage = PerimeterPatrolStage.AirCrossAfterFirstThird,
                        )
                    }
                }
                PerimeterPatrolStage.AscendSecondThird -> {
                    val y2 = PetEnginePerimeterPatrol.secondThirdTargetY(world)
                    if (ns.anchorYPx <= y2) {
                        val towardLeft = s.phase == PetRuntimePhase.WallRight
                        return PetEnginePerimeterPatrol.startCrossJump(
                            ns,
                            world,
                            towardLeft = towardLeft,
                            nextStage = PerimeterPatrolStage.AirCrossAfterSecondThird,
                        )
                    }
                }
                PerimeterPatrolStage.AscendToTop -> { }
                else -> { }
            }
        }

        if (s.perimeterPatrolEnabled && s.wallDescend) {
            when (s.perimeterStage) {
                PerimeterPatrolStage.DescendFirstThird -> {
                    val y1 = PetEnginePerimeterPatrol.firstDescendThirdThresholdY(world)
                    if (ns.anchorYPx >= y1) {
                        val towardLeft = s.phase == PetRuntimePhase.WallRight
                        return PetEnginePerimeterPatrol.startCrossJump(
                            ns,
                            world,
                            towardLeft = towardLeft,
                            nextStage = PerimeterPatrolStage.AirCrossDescendAfterFirstThird,
                        )
                    }
                }
                PerimeterPatrolStage.DescendSecondThird -> {
                    val y2 = PetEnginePerimeterPatrol.secondDescendThirdThresholdY(world)
                    if (ns.anchorYPx >= y2) {
                        val towardLeft = s.phase == PetRuntimePhase.WallRight
                        return PetEnginePerimeterPatrol.startCrossJump(
                            ns,
                            world,
                            towardLeft = towardLeft,
                            nextStage = PerimeterPatrolStage.AirCrossDescendAfterSecondThird,
                        )
                    }
                }
                PerimeterPatrolStage.DescendToFloor -> { }
                else -> { }
            }
        }

        if (!s.wallDescend && c.ceiling) {
            if (s.perimeterPatrolEnabled && s.perimeterStage == PerimeterPatrolStage.AscendToTop) {
                return PetEnginePerimeterPatrol.enterCeilingFromWall(ns, world)
            }
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
            val allowFloorSnap =
                !s.perimeterPatrolEnabled ||
                    s.perimeterStage == PerimeterPatrolStage.DescendToFloor
            if (allowFloorSnap) {
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
                        perimeterStage = PerimeterPatrolStage.BottomWalk,
                        frameIndex = 0,
                        msAccumulatedInFrame = 0f,
                    ),
                    world.playArea,
                )
            }
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

package com.weappsinc.screenpet.feature.pet.domain.usecase

import com.weappsinc.screenpet.core.constants.PetPhysicsConstants
import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.engine.PetBoundsGeometry
import com.weappsinc.screenpet.feature.pet.domain.model.PerimeterPatrolStage
import com.weappsinc.screenpet.feature.pet.domain.model.PetRuntimePhase
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState
import com.weappsinc.screenpet.feature.pet.domain.repository.PetSimulationRepository
import com.weappsinc.screenpet.feature.pet.domain.sync.PetSimulationUpdateMutex
import javax.inject.Inject
import kotlinx.coroutines.sync.withLock

/** Dat pet vao giua san + phase PreviewHold de xem clip ma khong di chuyen. */
class ShowcasePetClipUseCase @Inject constructor(
    private val repository: PetSimulationRepository,
    private val writeMutex: PetSimulationUpdateMutex,
) {
    suspend fun showClipAtCenter(clipId: ShimejiClipId): Result<Unit> = writeMutex.mutex.withLock {
        val world = repository.world.value
        val area = world.playArea
        val cx = area.widthPx / 2f
        val ay = PetBoundsGeometry.maxAnchorY(area)
        var s = world.snapshot.copy(
            phase = PetRuntimePhase.PreviewHold,
            clipId = clipId,
            anchorXPx = cx,
            anchorYPx = ay,
            frameIndex = 0,
            msAccumulatedInFrame = 0f,
            velocityXPxPerSec = 0f,
            velocityYPxPerSec = 0f,
            isDragging = false,
            bouncePhaseRemainingMs = 0L,
            wallDescend = false,
            perimeterPatrolEnabled = false,
            perimeterStage = PerimeterPatrolStage.BottomWalk,
        )
        s = PetBoundsGeometry.clampAnchor(s, area)
        repository.replace(
            world.clearedDragAnchors().copy(snapshot = s.copy(contact = PetBoundsGeometry.computeContact(s, area))),
        )
        Result.success(Unit)
    }

    suspend fun exitPreviewToWalking(): Result<Unit> = writeMutex.mutex.withLock {
        val world = repository.world.value
        if (world.snapshot.phase != PetRuntimePhase.PreviewHold) {
            return@withLock Result.success(Unit)
        }
        replaceWithGroundPatrolWalking(world)
        Result.success(Unit)
    }

    /** Bat lai di san + tuong tu overlay (bat ke phase truoc do). Dung man Shop preview. */
    suspend fun forceResumeGroundPatrol(): Result<Unit> = writeMutex.mutex.withLock {
        replaceWithGroundPatrolWalking(repository.world.value)
        Result.success(Unit)
    }

    private suspend fun replaceWithGroundPatrolWalking(world: PetWorldState) {
        val s = world.snapshot
        val vx = if (s.lookRight) {
            PetPhysicsConstants.WALK_SPEED_PX_PER_SEC
        } else {
            -PetPhysicsConstants.WALK_SPEED_PX_PER_SEC
        }
        val ay = PetBoundsGeometry.maxAnchorY(world.playArea)
        var ns = s.copy(
            phase = PetRuntimePhase.GroundMoving,
            clipId = ShimejiClipId.Walk,
            velocityXPxPerSec = vx,
            velocityYPxPerSec = 0f,
            anchorYPx = ay,
            frameIndex = 0,
            msAccumulatedInFrame = 0f,
            wallDescend = false,
            perimeterPatrolEnabled = true,
            perimeterStage = PerimeterPatrolStage.BottomWalk,
            isDragging = false,
            dragGrabOffsetXPx = 0f,
            dragGrabOffsetYPx = 0f,
            bouncePhaseRemainingMs = 0L,
        )
        ns = PetBoundsGeometry.clampAnchor(ns, world.playArea)
        repository.replace(
            world.clearedDragAnchors().copy(
                snapshot = ns.copy(contact = PetBoundsGeometry.computeContact(ns, world.playArea)),
            ),
        )
    }
}

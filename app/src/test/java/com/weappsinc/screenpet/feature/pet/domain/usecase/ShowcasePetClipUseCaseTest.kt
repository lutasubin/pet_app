package com.weappsinc.screenpet.feature.pet.domain.usecase

import com.weappsinc.screenpet.core.constants.PetPhysicsConstants
import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.model.PetBorderContact
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.model.PetRuntimePhase
import com.weappsinc.screenpet.feature.pet.domain.model.PetSnapshot
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState
import com.weappsinc.screenpet.feature.pet.domain.repository.PetSimulationRepository
import com.weappsinc.screenpet.feature.pet.domain.sync.PetSimulationUpdateMutex
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ShowcasePetClipUseCaseTest {

    private class FakeRepo(initial: PetWorldState) : PetSimulationRepository {
        private val flow = MutableStateFlow(initial)
        override val world = flow.asStateFlow()
        override suspend fun replace(newWorld: PetWorldState) {
            flow.value = newWorld
        }
    }

    @Test
    fun forceResumeGroundPatrol_tuPreviewHold_veGroundMovingWalk() = runBlocking {
        val area = PetPlayArea(400, 600)
        val snap = PetSnapshot(
            anchorXPx = 200f,
            anchorYPx = 580f,
            velocityXPxPerSec = 0f,
            velocityYPxPerSec = 0f,
            lookRight = true,
            clipId = ShimejiClipId.Stand,
            frameIndex = 3,
            msAccumulatedInFrame = 5f,
            phase = PetRuntimePhase.PreviewHold,
            contact = PetBorderContact(floor = true),
            isDragging = false,
            dragGrabOffsetXPx = 0f,
            dragGrabOffsetYPx = 0f,
            bouncePhaseRemainingMs = 0L,
        )
        val repo = FakeRepo(PetWorldState(area, snap))
        val useCase = ShowcasePetClipUseCase(repo, PetSimulationUpdateMutex())
        useCase.forceResumeGroundPatrol()
        val s = repo.world.value.snapshot
        assertEquals(PetRuntimePhase.GroundMoving, s.phase)
        assertEquals(ShimejiClipId.Walk, s.clipId)
        assertEquals(PetPhysicsConstants.WALK_SPEED_PX_PER_SEC, s.velocityXPxPerSec, 0.01f)
    }
}

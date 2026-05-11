package com.weappsinc.screenpet.feature.pet.domain.usecase

import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.model.PetBorderContact
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.model.PetRuntimePhase
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldDefaults
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState
import com.weappsinc.screenpet.feature.pet.domain.repository.PetSimulationRepository
import com.weappsinc.screenpet.feature.pet.domain.sync.PetSimulationUpdateMutex
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class TickPetUseCaseTest {

    private class FakeRepo(initial: PetWorldState) : PetSimulationRepository {
        private val flow = MutableStateFlow(initial)
        override val world = flow.asStateFlow()
        override suspend fun replace(newWorld: PetWorldState) {
            flow.value = newWorld
        }
    }

    @Test
    fun invoke_tick_thay_doi_frame_hoac_thoi_gian() = runBlocking {
        val area = PetPlayArea(400, 600)
        val initial = PetWorldDefaults.initial(area).let { w ->
            w.copy(
                snapshot = w.snapshot.copy(
                    phase = PetRuntimePhase.GroundMoving,
                    clipId = ShimejiClipId.Walk,
                    frameIndex = 0,
                    msAccumulatedInFrame = 0f,
                    contact = PetBorderContact(floor = true),
                ),
            )
        }
        val repo = FakeRepo(initial)
        val useCase = TickPetUseCase(repo, PetSimulationUpdateMutex())
        val beforeIdx = repo.world.value.snapshot.frameIndex
        val beforeMs = repo.world.value.snapshot.msAccumulatedInFrame
        useCase.invoke(6)
        val after = repo.world.value.snapshot
        val changed = after.frameIndex != beforeIdx || after.msAccumulatedInFrame != beforeMs
        assertTrue(changed)
    }
}

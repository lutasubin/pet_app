package com.weappsinc.screenpet.feature.pet.domain.usecase

import com.weappsinc.screenpet.feature.pet.domain.petsim.PetArenaInput
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetArenaSimulator
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetArenaState
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.repository.PetSimulationRepository
import com.weappsinc.screenpet.feature.pet.domain.sync.PetSimulationUpdateMutex
import javax.inject.Inject
import kotlinx.coroutines.sync.withLock

class UpdatePlayAreaUseCase @Inject constructor(
    private val repository: PetSimulationRepository,
    private val writeMutex: PetSimulationUpdateMutex,
) {
    suspend operator fun invoke(playArea: PetPlayArea): Result<Unit> = writeMutex.mutex.withLock {
        val cur = repository.world.value
        val arena = PetArenaState.fromPrimaryWorld(cur)
        val nextArena = PetArenaSimulator.advance(arena, PetArenaInput.Layout(playArea))
            .getOrElse { return@withLock Result.failure(it) }
        repository.replace(nextArena.toPrimaryWorld())
        Result.success(Unit)
    }
}

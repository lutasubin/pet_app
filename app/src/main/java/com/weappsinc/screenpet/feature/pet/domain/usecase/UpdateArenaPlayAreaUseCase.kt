package com.weappsinc.screenpet.feature.pet.domain.usecase

import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetArenaInput
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetArenaSimulator
import com.weappsinc.screenpet.feature.pet.domain.repository.PetArenaRepository
import com.weappsinc.screenpet.feature.pet.domain.sync.PetSimulationUpdateMutex
import javax.inject.Inject
import kotlinx.coroutines.sync.withLock

class UpdateArenaPlayAreaUseCase @Inject constructor(
    private val repository: PetArenaRepository,
    private val writeMutex: PetSimulationUpdateMutex,
) {
    suspend operator fun invoke(playArea: PetPlayArea): Result<Unit> = writeMutex.mutex.withLock {
        val cur = repository.arena.value
        val next = PetArenaSimulator.advance(cur, PetArenaInput.Layout(playArea))
            .getOrElse { return@withLock Result.failure(it) }
        repository.replace(next)
        Result.success(Unit)
    }
}

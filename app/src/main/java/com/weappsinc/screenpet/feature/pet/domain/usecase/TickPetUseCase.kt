package com.weappsinc.screenpet.feature.pet.domain.usecase

import com.weappsinc.screenpet.feature.pet.domain.engine.PetEngine
import com.weappsinc.screenpet.feature.pet.domain.model.PetInput
import com.weappsinc.screenpet.feature.pet.domain.repository.PetSimulationRepository
import com.weappsinc.screenpet.feature.pet.domain.sync.PetSimulationUpdateMutex
import javax.inject.Inject
import kotlinx.coroutines.sync.withLock

class TickPetUseCase @Inject constructor(
    private val repository: PetSimulationRepository,
    private val writeMutex: PetSimulationUpdateMutex,
) {
    suspend operator fun invoke(dtMs: Long): Result<Unit> = writeMutex.mutex.withLock {
        val cur = repository.world.value
        val next = PetEngine.advance(cur, PetInput.Tick(dtMs)).getOrElse { return@withLock Result.failure(it) }
        repository.replace(next)
        Result.success(Unit)
    }
}

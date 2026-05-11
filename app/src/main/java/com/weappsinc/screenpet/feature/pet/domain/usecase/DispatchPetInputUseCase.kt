package com.weappsinc.screenpet.feature.pet.domain.usecase

import com.weappsinc.screenpet.feature.pet.domain.engine.PetEngine
import com.weappsinc.screenpet.feature.pet.domain.model.PetInput
import com.weappsinc.screenpet.feature.pet.domain.repository.PetSimulationRepository
import com.weappsinc.screenpet.feature.pet.domain.sync.PetSimulationUpdateMutex
import javax.inject.Inject
import kotlinx.coroutines.sync.withLock

/** Xu ly su kien pointer / layout; khong dung cho [PetInput.Tick]. */
class DispatchPetInputUseCase @Inject constructor(
    private val repository: PetSimulationRepository,
    private val writeMutex: PetSimulationUpdateMutex,
) {
    suspend operator fun invoke(input: PetInput): Result<Unit> = writeMutex.mutex.withLock {
        if (input is PetInput.Tick) {
            return@withLock Result.failure(IllegalArgumentException("Tick phai qua TickPetUseCase"))
        }
        val cur = repository.world.value
        val next = PetEngine.advance(cur, input).getOrElse { return@withLock Result.failure(it) }
        repository.replace(next)
        Result.success(Unit)
    }
}

package com.weappsinc.screenpet.feature.pet.domain.usecase

import com.weappsinc.screenpet.feature.pet.domain.engine.PetEngine
import com.weappsinc.screenpet.feature.pet.domain.model.PetInput
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
        val next = PetEngine.advance(cur, PetInput.Layout(playArea)).getOrElse { return@withLock Result.failure(it) }
        repository.replace(next)
        Result.success(Unit)
    }
}

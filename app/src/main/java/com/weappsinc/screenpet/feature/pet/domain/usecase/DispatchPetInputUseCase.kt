package com.weappsinc.screenpet.feature.pet.domain.usecase

import com.weappsinc.screenpet.feature.pet.domain.model.PetInput
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetArenaInput
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetArenaSimulator
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetArenaState
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetId
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
        val arena = PetArenaState.fromPrimaryWorld(cur)
        val arenaInput = toArenaInput(input)
        val nextArena = PetArenaSimulator.advance(arena, arenaInput)
            .getOrElse { return@withLock Result.failure(it) }
        repository.replace(nextArena.toPrimaryWorld())
        Result.success(Unit)
    }

    private fun toArenaInput(input: PetInput): PetArenaInput = when (input) {
        is PetInput.Layout -> PetArenaInput.Layout(input.playArea)
        is PetInput.PointerDown ->
            PetArenaInput.PointerDown(PetId.DEFAULT, input.xPx, input.yPx)
        is PetInput.PointerMove ->
            PetArenaInput.PointerMove(PetId.DEFAULT, input.xPx, input.yPx)
        is PetInput.PointerUp ->
            PetArenaInput.PointerUp(
                PetId.DEFAULT,
                input.releaseVelocityXPxPerSec,
                input.releaseVelocityYPxPerSec,
            )
        is PetInput.Tick -> error("Tick phai qua TickPetUseCase")
    }
}

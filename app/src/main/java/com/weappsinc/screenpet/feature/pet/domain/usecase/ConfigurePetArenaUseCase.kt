package com.weappsinc.screenpet.feature.pet.domain.usecase

import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldDefaults
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetArenaState
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetEntityState
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetId
import com.weappsinc.screenpet.feature.pet.domain.repository.PetArenaRepository
import com.weappsinc.screenpet.feature.pet.domain.sync.PetSimulationUpdateMutex
import javax.inject.Inject
import kotlinx.coroutines.sync.withLock

/** Mot pet bo sung trong swarm: id + thu muc asset (vi du "data1/Ace"). */
data class PetArenaSpec(val id: PetId, val assetFolder: String)

/** Rebuild PetArenaState theo danh sach mong muon, giu lai state pet cu neu id trung. */
class ConfigurePetArenaUseCase @Inject constructor(
    private val repository: PetArenaRepository,
    private val writeMutex: PetSimulationUpdateMutex,
) {
    suspend operator fun invoke(
        playArea: PetPlayArea,
        pets: List<PetArenaSpec>,
    ): Result<Unit> = writeMutex.mutex.withLock {
        runCatching {
            val cur = repository.arena.value
            val nextPets = pets.associate { spec ->
                val existing = cur.pets[spec.id]
                spec.id to (existing?.copy(assetFolder = spec.assetFolder)
                    ?: PetEntityState.fromWorld(
                        PetWorldDefaults.initial(playArea),
                        assetFolder = spec.assetFolder,
                    ))
            }
            repository.replace(PetArenaState(playArea = playArea, pets = nextPets))
        }
    }
}

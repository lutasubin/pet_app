package com.weappsinc.screenpet.feature.home.domain.usecase

import com.weappsinc.screenpet.feature.home.domain.model.HomeSettings
import com.weappsinc.screenpet.feature.home.domain.model.ShimejiCharacter
import com.weappsinc.screenpet.feature.home.domain.repository.HomeSettingsRepository
import com.weappsinc.screenpet.feature.home.domain.repository.ShimejiUnlockRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.first

/** Kiem tra slot mo: chua chon pet nao (tat ca null). */
internal object ApplyRandomShimejiMixLogic {
    fun hasNoSelectionInUnlockedSlots(settings: HomeSettings): Boolean {
        val n = settings.unlockedSlotCount
        return (0 until n).all { settings.selectedSlotIds[it] == null }
    }
}

/**
 * Luu so pet mix (1..6). Neu chua chon pet o slot mo: random gan pet san sang (unlock+download).
 * N > 1 thi bat Swarm.
 */
class ApplyRandomShimejiMixUseCase @Inject constructor(
    private val homeSettingsRepository: HomeSettingsRepository,
    private val unlockRepository: ShimejiUnlockRepository,
) {
    suspend operator fun invoke(
        catalog: List<ShimejiCharacter>,
        requestedCount: Int,
    ): Result<Unit> = runCatching {
        val safeCount = requestedCount.coerceIn(1, HomeSettings.SLOT_COUNT)
        homeSettingsRepository.setMixRandomPetCount(safeCount)
        val settings = homeSettingsRepository.settings.first()
        if (!ApplyRandomShimejiMixLogic.hasNoSelectionInUnlockedSlots(settings)) {
            return@runCatching
        }
        val unlocked = unlockRepository.unlockedIds.first()
        val downloaded = unlockRepository.downloadedIds.first()
        val readyIds = catalog
            .map { it.id }
            .filter { it in unlocked && it in downloaded }
            .distinct()
        if (readyIds.isEmpty()) {
            error("no_ready_shimeji")
        }
        val unlockedSlots = settings.unlockedSlotCount
        val pickCount = minOf(safeCount, readyIds.size, unlockedSlots)
        val chosen = readyIds.shuffled().take(pickCount)
        chosen.forEachIndexed { index, id ->
            homeSettingsRepository.setSlot(index, id)
        }
        if (pickCount > 1) {
            homeSettingsRepository.setSwarm(true)
        }
    }
}

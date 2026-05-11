package com.weappsinc.screenpet.feature.home.domain.usecase

import com.weappsinc.screenpet.feature.home.domain.model.HomeSettings
import com.weappsinc.screenpet.feature.home.domain.repository.HomeSettingsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.first

/** Mo khoa them 1 slot (toi da 6 slot). */
class UnlockNextSlotUseCase @Inject constructor(
    private val repository: HomeSettingsRepository,
) {
    suspend operator fun invoke() {
        val current = repository.settings.first().unlockedSlotCount
        val next = (current + 1).coerceAtMost(HomeSettings.SLOT_COUNT)
        repository.setUnlockedSlotCount(next)
    }
}

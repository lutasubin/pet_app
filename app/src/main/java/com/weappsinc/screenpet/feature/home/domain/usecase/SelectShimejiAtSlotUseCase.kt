package com.weappsinc.screenpet.feature.home.domain.usecase

import com.weappsinc.screenpet.feature.home.domain.repository.HomeSettingsRepository
import javax.inject.Inject

class SelectShimejiAtSlotUseCase @Inject constructor(
    private val repository: HomeSettingsRepository,
) {
    suspend operator fun invoke(slot: Int, characterId: String?) {
        repository.setSlot(slot, characterId)
    }
}

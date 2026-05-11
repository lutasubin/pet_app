package com.weappsinc.screenpet.feature.home.domain.usecase

import com.weappsinc.screenpet.feature.home.domain.repository.HomeSettingsRepository
import javax.inject.Inject

class ToggleSwarmUseCase @Inject constructor(
    private val repository: HomeSettingsRepository,
) {
    suspend operator fun invoke(enabled: Boolean) {
        repository.setSwarm(enabled)
    }
}

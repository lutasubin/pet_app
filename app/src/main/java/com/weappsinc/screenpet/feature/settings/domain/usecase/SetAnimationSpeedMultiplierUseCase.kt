package com.weappsinc.screenpet.feature.settings.domain.usecase

import com.weappsinc.screenpet.feature.settings.domain.repository.AppSettingsRepository
import javax.inject.Inject

class SetAnimationSpeedMultiplierUseCase @Inject constructor(
    private val repository: AppSettingsRepository,
) {
    suspend operator fun invoke(mult: Float) {
        repository.setAnimationSpeedMultiplier(mult)
    }
}

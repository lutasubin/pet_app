package com.weappsinc.screenpet.feature.settings.domain.usecase

import com.weappsinc.screenpet.feature.settings.domain.repository.AppSettingsRepository
import javax.inject.Inject

class SetAnimationSizeScaleUseCase @Inject constructor(
    private val repository: AppSettingsRepository,
) {
    suspend operator fun invoke(scale: Float) {
        repository.setAnimationSizeScale(scale)
    }
}

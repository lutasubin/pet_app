package com.weappsinc.screenpet.feature.settings.domain.usecase

import com.weappsinc.screenpet.feature.settings.domain.model.AppSettings
import com.weappsinc.screenpet.feature.settings.domain.repository.AppSettingsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveAppSettingsUseCase @Inject constructor(
    private val repository: AppSettingsRepository,
) {
    operator fun invoke(): Flow<AppSettings> = repository.settings
}

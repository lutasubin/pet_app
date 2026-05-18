package com.weappsinc.screenpet.feature.settings.domain.usecase

import com.weappsinc.screenpet.feature.settings.domain.repository.AppRatingRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveSavedAppRatingUseCase @Inject constructor(
    private val repository: AppRatingRepository,
) {
    operator fun invoke(): Flow<Int?> = repository.savedStars
}

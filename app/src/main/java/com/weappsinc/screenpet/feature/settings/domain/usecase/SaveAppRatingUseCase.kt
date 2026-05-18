package com.weappsinc.screenpet.feature.settings.domain.usecase

import com.weappsinc.screenpet.feature.settings.domain.repository.AppRatingRepository
import javax.inject.Inject

class SaveAppRatingUseCase @Inject constructor(
    private val repository: AppRatingRepository,
) {
    suspend operator fun invoke(stars: Int) {
        repository.saveStars(stars)
    }
}

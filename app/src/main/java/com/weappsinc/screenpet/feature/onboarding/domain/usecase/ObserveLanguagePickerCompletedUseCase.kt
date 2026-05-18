package com.weappsinc.screenpet.feature.onboarding.domain.usecase

import com.weappsinc.screenpet.feature.onboarding.domain.repository.OnboardingRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveLanguagePickerCompletedUseCase @Inject constructor(
    private val repository: OnboardingRepository,
) {
    operator fun invoke(): Flow<Boolean> = repository.observeLanguagePickerCompleted()
}

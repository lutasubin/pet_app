package com.weappsinc.screenpet.feature.onboarding.domain.usecase

import com.weappsinc.screenpet.feature.onboarding.domain.repository.OnboardingRepository
import javax.inject.Inject

class MarkLanguagePickerCompletedUseCase @Inject constructor(
    private val repository: OnboardingRepository,
) {
    suspend operator fun invoke() {
        repository.markLanguagePickerCompleted()
    }
}

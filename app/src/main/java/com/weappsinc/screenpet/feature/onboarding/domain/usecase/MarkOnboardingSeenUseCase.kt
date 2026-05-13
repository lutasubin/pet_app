package com.weappsinc.screenpet.feature.onboarding.domain.usecase

import com.weappsinc.screenpet.feature.onboarding.domain.repository.OnboardingRepository
import javax.inject.Inject

/** Danh dau nguoi dung da xem het onboarding (chi goi 1 lan khi finish). */
class MarkOnboardingSeenUseCase @Inject constructor(
    private val repository: OnboardingRepository,
) {
    suspend operator fun invoke() {
        repository.markSeen()
    }
}

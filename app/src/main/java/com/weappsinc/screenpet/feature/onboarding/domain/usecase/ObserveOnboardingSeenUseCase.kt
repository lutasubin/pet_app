package com.weappsinc.screenpet.feature.onboarding.domain.usecase

import com.weappsinc.screenpet.feature.onboarding.domain.repository.OnboardingRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/** Lay co "da xem onboarding" duoi dang Flow de presentation reactive. */
class ObserveOnboardingSeenUseCase @Inject constructor(
    private val repository: OnboardingRepository,
) {
    operator fun invoke(): Flow<Boolean> = repository.observeSeen()
}

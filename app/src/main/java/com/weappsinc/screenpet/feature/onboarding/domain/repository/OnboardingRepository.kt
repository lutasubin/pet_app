package com.weappsinc.screenpet.feature.onboarding.domain.repository

import kotlinx.coroutines.flow.Flow

/** Co "da xem onboarding" — flag chi luu 1 chieu (false -> true) trong DataStore. */
interface OnboardingRepository {
    fun observeSeen(): Flow<Boolean>
    suspend fun markSeen()

    fun observeLanguagePickerCompleted(): Flow<Boolean>
    suspend fun markLanguagePickerCompleted()
}

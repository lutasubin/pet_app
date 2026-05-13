package com.weappsinc.screenpet.feature.onboarding.domain.usecase

import com.weappsinc.screenpet.feature.onboarding.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ObserveOnboardingSeenUseCaseTest {

    @Test
    fun forward_gia_tri_tu_repository() = runBlocking {
        val useCaseTrue = ObserveOnboardingSeenUseCase(FakeRepo(flowOf(true)))
        val useCaseFalse = ObserveOnboardingSeenUseCase(FakeRepo(flowOf(false)))

        assertEquals(true, useCaseTrue().first())
        assertEquals(false, useCaseFalse().first())
    }

    private class FakeRepo(private val source: Flow<Boolean>) : OnboardingRepository {
        override fun observeSeen(): Flow<Boolean> = source
        override suspend fun markSeen() = Unit
    }
}

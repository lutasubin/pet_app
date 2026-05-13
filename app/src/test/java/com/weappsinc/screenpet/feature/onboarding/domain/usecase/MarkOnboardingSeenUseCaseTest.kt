package com.weappsinc.screenpet.feature.onboarding.domain.usecase

import com.weappsinc.screenpet.feature.onboarding.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class MarkOnboardingSeenUseCaseTest {

    @Test
    fun invoke_goi_markSeen_dung_1_lan() = runBlocking {
        val fake = FakeOnboardingRepository()
        val useCase = MarkOnboardingSeenUseCase(fake)

        useCase()

        assertEquals(1, fake.markSeenCallCount)
    }

    private class FakeOnboardingRepository : OnboardingRepository {
        var markSeenCallCount: Int = 0
        override fun observeSeen(): Flow<Boolean> = flowOf(false)
        override suspend fun markSeen() {
            markSeenCallCount += 1
        }
    }
}

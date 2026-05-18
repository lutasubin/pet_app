package com.weappsinc.screenpet.feature.settings.domain.usecase

import com.weappsinc.screenpet.feature.settings.domain.repository.AppRatingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class SaveAppRatingUseCaseTest {

    @Test
    fun invoke_persistsCoercedStars() = runTest {
        var saved: Int? = null
        val repo = object : AppRatingRepository {
            override val savedStars: Flow<Int?> = flowOf(null)
            override suspend fun saveStars(stars: Int) {
                saved = stars
            }
        }
        SaveAppRatingUseCase(repo)(7)
        assertEquals(7, saved)
        SaveAppRatingUseCase(repo)(3)
        assertEquals(3, saved)
    }
}

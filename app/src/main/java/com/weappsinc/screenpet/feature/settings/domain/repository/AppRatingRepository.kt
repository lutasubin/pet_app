package com.weappsinc.screenpet.feature.settings.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppRatingRepository {
    /** null neu chua tung luu danh gia. */
    val savedStars: Flow<Int?>

    suspend fun saveStars(stars: Int)
}

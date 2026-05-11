package com.weappsinc.screenpet.feature.home.domain.repository

import kotlinx.coroutines.flow.Flow

/** Quan ly trang thai unlock cua tung Shimeji (luu DataStore). */
interface ShimejiUnlockRepository {
    val unlockedIds: Flow<Set<String>>
    val downloadedIds: Flow<Set<String>>
    suspend fun unlock(id: String)
    suspend fun markDownloaded(id: String)
    suspend fun replaceAll(ids: Set<String>)
    suspend fun replaceDownloaded(ids: Set<String>)
}

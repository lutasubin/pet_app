package com.weappsinc.screenpet.feature.home.domain.repository

import com.weappsinc.screenpet.feature.home.domain.model.ShimejiCharacter

/** Doc catalog Shimeji tu asset (data1 + data2). */
interface ShimejiCatalogRepository {
    suspend fun loadAll(): Result<List<ShimejiCharacter>>
}

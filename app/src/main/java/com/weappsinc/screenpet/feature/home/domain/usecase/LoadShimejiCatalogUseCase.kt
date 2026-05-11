package com.weappsinc.screenpet.feature.home.domain.usecase

import com.weappsinc.screenpet.feature.home.domain.model.ShimejiCharacter
import com.weappsinc.screenpet.feature.home.domain.repository.ShimejiCatalogRepository
import javax.inject.Inject

class LoadShimejiCatalogUseCase @Inject constructor(
    private val repository: ShimejiCatalogRepository,
) {
    suspend operator fun invoke(): Result<List<ShimejiCharacter>> = repository.loadAll()
}

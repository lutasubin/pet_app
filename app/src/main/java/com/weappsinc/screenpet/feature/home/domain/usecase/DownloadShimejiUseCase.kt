package com.weappsinc.screenpet.feature.home.domain.usecase

import com.weappsinc.screenpet.feature.home.domain.repository.ShimejiUnlockRepository
import javax.inject.Inject

class DownloadShimejiUseCase @Inject constructor(
    private val repository: ShimejiUnlockRepository,
) {
    suspend operator fun invoke(characterId: String) {
        repository.markDownloaded(characterId)
    }
}

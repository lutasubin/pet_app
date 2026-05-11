package com.weappsinc.screenpet.feature.home.domain.usecase

import com.weappsinc.screenpet.feature.home.domain.repository.ShimejiUnlockRepository
import javax.inject.Inject

class UnlockShimejiUseCase @Inject constructor(
    private val repository: ShimejiUnlockRepository,
) {
    suspend operator fun invoke(characterId: String) {
        repository.unlock(characterId)
    }
}

package com.weappsinc.screenpet.feature.home.domain.usecase

import com.weappsinc.screenpet.feature.home.domain.repository.ShimejiUnlockRepository
import javax.inject.Inject

/** Theo rule moi: khong mo khoa mac dinh, unlock tai Shop. */
class SeedDefaultUnlockedUseCase @Inject constructor(
    private val unlockRepository: ShimejiUnlockRepository,
) {
    suspend operator fun invoke(): Result<Unit> = runCatching {
        // Buoc chuyen doi theo yeu cau: tat ca pet deu khoa, phai unlock roi moi download.
        unlockRepository.replaceAll(emptySet())
        unlockRepository.replaceDownloaded(emptySet())
    }
}

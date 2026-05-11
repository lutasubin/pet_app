package com.weappsinc.screenpet.feature.home.domain.usecase

import com.weappsinc.screenpet.feature.home.domain.model.HomeSettings
import com.weappsinc.screenpet.feature.home.domain.model.ShimejiCharacter
import com.weappsinc.screenpet.feature.home.domain.repository.HomeSettingsRepository
import com.weappsinc.screenpet.feature.home.domain.repository.ShimejiUnlockRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

/** Trang thai tong hop cua Home (chua catalog vi catalog tinh, load 1 lan trong VM). */
data class HomeObservableState(
    val settings: HomeSettings,
    val unlockedIds: Set<String>,
    val downloadedIds: Set<String>,
)

class ObserveHomeStateUseCase @Inject constructor(
    private val settingsRepository: HomeSettingsRepository,
    private val unlockRepository: ShimejiUnlockRepository,
) {
    operator fun invoke(): Flow<HomeObservableState> =
        combine(
            settingsRepository.settings,
            unlockRepository.unlockedIds,
            unlockRepository.downloadedIds,
        ) { s, u, d ->
            HomeObservableState(s, u, d)
        }

    @Suppress("unused")
    fun characters(catalog: List<ShimejiCharacter>): List<ShimejiCharacter> = catalog
}

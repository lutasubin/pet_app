package com.weappsinc.screenpet.feature.home.presentation.nav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weappsinc.screenpet.feature.home.domain.usecase.DownloadShimejiUseCase
import com.weappsinc.screenpet.feature.home.domain.usecase.LoadShimejiCatalogUseCase
import com.weappsinc.screenpet.feature.home.domain.usecase.ObserveHomeStateUseCase
import com.weappsinc.screenpet.feature.home.domain.usecase.UnlockShimejiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val loadCatalog: LoadShimejiCatalogUseCase,
    private val observeHomeState: ObserveHomeStateUseCase,
    private val unlockShimejiUseCase: UnlockShimejiUseCase,
    private val downloadShimejiUseCase: DownloadShimejiUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShopUiState())
    val uiState: StateFlow<ShopUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val catalog = loadCatalog().getOrDefault(emptyList())
            _uiState.update { it.copy(catalog = catalog, isLoading = false) }
            observeHomeState().collect { state ->
                _uiState.update {
                    it.copy(
                        unlockedIds = state.unlockedIds,
                        downloadedIds = state.downloadedIds,
                    )
                }
            }
        }
    }

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    fun onUnlock(characterId: String) {
        viewModelScope.launch { unlockShimejiUseCase(characterId) }
    }

    fun onDownload(characterId: String) {
        val current = _uiState.value
        if (current.downloadingId != null) return
        if (characterId !in current.unlockedIds) return
        viewModelScope.launch {
            _uiState.update { it.copy(downloadingId = characterId, downloadProgress = 30) }
            delay(400)
            _uiState.update { it.copy(downloadProgress = 60) }
            delay(400)
            _uiState.update { it.copy(downloadProgress = 85) }
            delay(400)
            downloadShimejiUseCase(characterId)
            _uiState.update { it.copy(downloadingId = null, downloadProgress = 0) }
        }
    }
}

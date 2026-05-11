package com.weappsinc.screenpet.feature.splash.presentation

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weappsinc.screenpet.feature.home.domain.usecase.LoadShimejiCatalogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loadShimejiCatalog: LoadShimejiCatalogUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val catalogJob = async(Dispatchers.IO) {
                loadShimejiCatalog().getOrDefault(emptyList())
            }
            val minShowMs = 4200L
            val t0 = SystemClock.uptimeMillis()
            while (true) {
                delay(50L)
                val elapsed = SystemClock.uptimeMillis() - t0
                val catalogReady = catalogJob.isCompleted
                val timeLine = (elapsed / minShowMs.toFloat()).coerceIn(0f, 1f)
                val progress = computeSplashProgress(
                    timeLine = timeLine,
                    catalogReady = catalogReady,
                    elapsedPastMin = (elapsed - minShowMs).coerceAtLeast(0L),
                )
                _uiState.update { it.copy(progress = progress) }
                if (catalogReady && elapsed >= minShowMs) break
            }
            catalogJob.await()
            _uiState.update { SplashUiState(progress = 1f, finished = true) }
        }
    }
}

/**
 * Thanh tien trinh tang deu theo thoi gian (0 -> ~0.97 trong minShow).
 * Catalog chua xong sau minShow: nhich rat cham them toi ~0.99.
 * Chi ve 1f khi catalog xong va da het minShow (SplashViewModel break).
 */
internal fun computeSplashProgress(
    timeLine: Float,
    catalogReady: Boolean,
    elapsedPastMin: Long,
): Float {
    val cap = 0.97f
    val linear = (timeLine * cap).coerceIn(0f, cap)
    if (catalogReady && timeLine >= 1f) return 1f
    if (!catalogReady && elapsedPastMin > 0L) {
        val creep = (elapsedPastMin / 12000f) * (0.99f - cap)
        return (cap + creep).coerceAtMost(0.99f)
    }
    return linear
}

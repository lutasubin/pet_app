package com.weappsinc.screenpet.feature.pet.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weappsinc.screenpet.core.constants.PetDemoActionPlaylist
import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.model.PetInput
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.repository.PetSimulationRepository
import com.weappsinc.screenpet.feature.pet.domain.usecase.DispatchPetInputUseCase
import com.weappsinc.screenpet.feature.pet.domain.usecase.ShowcasePetClipUseCase
import com.weappsinc.screenpet.feature.pet.domain.usecase.TickPetUseCase
import com.weappsinc.screenpet.feature.pet.domain.usecase.UpdatePlayAreaUseCase
import com.weappsinc.screenpet.feature.pet.presentation.overlay.PetOverlaySession
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@HiltViewModel
class PetViewModel @Inject constructor(
    private val repository: PetSimulationRepository,
    private val tickPetUseCase: TickPetUseCase,
    private val updatePlayAreaUseCase: UpdatePlayAreaUseCase,
    private val dispatchPetInputUseCase: DispatchPetInputUseCase,
    private val showcasePetClipUseCase: ShowcasePetClipUseCase,
    private val overlaySession: PetOverlaySession,
) : ViewModel(), PetPlayEventSink {

    val world = repository.world
    val overlayActive = overlaySession.active

    private var tickJob: Job? = null
    private var clipShowcaseJob: Job? = null

    init {
        viewModelScope.launch {
            overlaySession.active.collect { active ->
                if (active) {
                    launch {
                        stopAllDemosInternal()
                        showcasePetClipUseCase.exitPreviewToWalking()
                    }
                }
                tickJob?.cancel()
                tickJob = null
                if (!active) {
                    startSimulationLoop()
                }
            }
        }
    }

    fun startSimulationLoop() {
        if (tickJob?.isActive == true) return
        tickJob = viewModelScope.launch {
            while (isActive) {
                delay(TICK_MS)
                tickPetUseCase(TICK_MS)
            }
        }
    }

    fun stopAllDemos() {
        viewModelScope.launch {
            stopAllDemosInternal()
            showcasePetClipUseCase.exitPreviewToWalking()
            if (!overlaySession.active.value) {
                startSimulationLoop()
            }
        }
    }

    /** Man Shop preview: engine di san + tuong tu overlay, khong chay playlist demo. */
    fun startShopPreviewLiveMode() {
        viewModelScope.launch {
            if (overlaySession.active.value) return@launch
            stopAllDemosInternal()
            showcasePetClipUseCase.forceResumeGroundPatrol()
            startSimulationLoop()
        }
    }

    /** Demo hanh dong: lap lai playlist clip (gom nhieu frame trong 46 anh), khong quet shime1..46. */
    fun startDemoActionPlaylist() {
        viewModelScope.launch {
            if (overlaySession.active.value) return@launch
            stopAllDemosInternal()
            showcasePetClipUseCase.exitPreviewToWalking()
            startSimulationLoop()
            val playlist = PetDemoActionPlaylist.entries
            clipShowcaseJob = viewModelScope.launch {
                while (isActive) {
                    for ((clip, ms) in playlist) {
                        showcasePetClipUseCase.showClipAtCenter(clip)
                        delay(ms)
                    }
                }
            }
        }
    }

    fun startDemoAllClips() {
        viewModelScope.launch {
            if (overlaySession.active.value) return@launch
            stopAllDemosInternal()
            startSimulationLoop()
            val clips = ShimejiClipId.entries.toList()
            clipShowcaseJob = viewModelScope.launch {
                var idx = 0
                while (isActive) {
                    showcasePetClipUseCase.showClipAtCenter(clips[idx % clips.size])
                    delay(CLIP_SHOWCASE_MS)
                    idx++
                }
            }
        }
    }

    private suspend fun stopAllDemosInternal() {
        clipShowcaseJob?.cancel()
        clipShowcaseJob = null
    }

    override fun onPlayAreaSized(widthPx: Int, heightPx: Int) {
        if (widthPx <= 0 || heightPx <= 0) return
        viewModelScope.launch {
            updatePlayAreaUseCase(PetPlayArea(widthPx, heightPx))
        }
    }

    override fun onPointerDown(xPx: Float, yPx: Float) {
        viewModelScope.launch { dispatchPetInputUseCase(PetInput.PointerDown(xPx, yPx)) }
    }

    override fun onPointerMove(xPx: Float, yPx: Float) {
        viewModelScope.launch { dispatchPetInputUseCase(PetInput.PointerMove(xPx, yPx)) }
    }

    override fun onPointerUp(releaseVelocityXPxPerSec: Float, releaseVelocityYPxPerSec: Float) {
        viewModelScope.launch {
            dispatchPetInputUseCase(
                PetInput.PointerUp(releaseVelocityXPxPerSec, releaseVelocityYPxPerSec),
            )
        }
    }

    companion object {
        private const val TICK_MS: Long = 16L
        private const val CLIP_SHOWCASE_MS: Long = 3200L
    }
}

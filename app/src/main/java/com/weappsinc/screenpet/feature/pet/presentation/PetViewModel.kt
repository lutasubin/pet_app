package com.weappsinc.screenpet.feature.pet.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weappsinc.screenpet.feature.pet.domain.model.PetInput
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.repository.PetSimulationRepository
import com.weappsinc.screenpet.feature.pet.domain.usecase.DispatchPetInputUseCase
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
    private val overlaySession: PetOverlaySession,
) : ViewModel(), PetPlayEventSink {

    val world = repository.world
    val overlayActive = overlaySession.active

    private var tickJob: Job? = null

    init {
        viewModelScope.launch {
            overlaySession.active.collect { active ->
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

    override fun onPointerUp() {
        viewModelScope.launch { dispatchPetInputUseCase(PetInput.PointerUp()) }
    }

    companion object {
        private const val TICK_MS: Long = 16L
    }
}

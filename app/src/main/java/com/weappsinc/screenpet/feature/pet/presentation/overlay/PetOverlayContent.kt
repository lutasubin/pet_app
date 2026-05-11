package com.weappsinc.screenpet.feature.pet.presentation.overlay

import androidx.compose.runtime.Composable
import com.weappsinc.screenpet.feature.pet.domain.repository.PetSimulationRepository
import com.weappsinc.screenpet.feature.pet.presentation.PetPlayEventSink

@Composable
fun PetOverlayContent(
    repository: PetSimulationRepository,
    eventSink: PetPlayEventSink,
) {
    PetOverlaySpriteHost(repository = repository, eventSink = eventSink)
}

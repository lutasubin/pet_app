package com.weappsinc.screenpet.feature.pet.presentation.overlay

import android.app.Service
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetId
import com.weappsinc.screenpet.feature.pet.domain.repository.PetArenaRepository
import com.weappsinc.screenpet.feature.pet.domain.usecase.DispatchArenaInputUseCase
import com.weappsinc.screenpet.feature.pet.domain.usecase.UpdateArenaPlayAreaUseCase
import com.weappsinc.screenpet.feature.settings.domain.model.AppSettings
import kotlinx.coroutines.CoroutineScope

/** Quan ly map PetId -> window/position-sync; them/xoa khi arena.pets thay doi. */
class PetArenaOverlayHostManager(
    private val service: Service,
    private val scope: CoroutineScope,
    private val repository: PetArenaRepository,
    private val updateArenaPlayAreaUseCase: UpdateArenaPlayAreaUseCase,
    private val dispatchArenaInputUseCase: DispatchArenaInputUseCase,
) {
    private val hosts = mutableMapOf<PetId, PetArenaOverlayWindowHost>()
    private val syncs = mutableMapOf<PetId, PetArenaOverlayPositionSync>()
    private var latestSettings: AppSettings = AppSettings()

    fun sync(activeIds: Set<PetId>, appSettings: AppSettings) {
        latestSettings = appSettings
        hosts.values.forEach { it.applyAppSettings(appSettings) }
        val toAdd = activeIds - hosts.keys
        val toRemove = hosts.keys - activeIds
        toAdd.forEach(::addPet)
        toRemove.forEach(::removePet)
    }

    fun detachAll() {
        hosts.keys.toList().forEach(::removePet)
    }

    private fun addPet(petId: PetId) {
        val sink = PetArenaOverlayPlaySink(
            scope = scope,
            petId = petId,
            updatePlayArea = updateArenaPlayAreaUseCase,
            dispatchArenaInput = dispatchArenaInputUseCase,
        )
        val host = PetArenaOverlayWindowHost(service, repository, petId, sink).also { it.attach(latestSettings) }
        val sync = PetArenaOverlayPositionSync(scope, repository, petId, host).also { it.start() }
        hosts[petId] = host
        syncs[petId] = sync
    }

    private fun removePet(petId: PetId) {
        syncs.remove(petId)?.stop()
        hosts.remove(petId)?.detach()
    }
}

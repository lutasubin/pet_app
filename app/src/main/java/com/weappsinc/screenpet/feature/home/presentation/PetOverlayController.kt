package com.weappsinc.screenpet.feature.home.presentation

import android.content.Context
import com.weappsinc.screenpet.feature.home.domain.model.HomeSettings
import com.weappsinc.screenpet.feature.home.domain.model.ShimejiCharacter
import com.weappsinc.screenpet.feature.pet.domain.petsim.PetId
import com.weappsinc.screenpet.feature.pet.domain.repository.PetArenaRepository
import com.weappsinc.screenpet.feature.pet.domain.usecase.ConfigurePetArenaUseCase
import com.weappsinc.screenpet.feature.pet.domain.usecase.PetArenaSpec
import com.weappsinc.screenpet.feature.pet.presentation.overlay.PetOverlayService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Facade tu Home -> arena + overlay service.
 * Goi [apply] moi khi HomeSettings, unlocked, hoac catalog doi.
 */
@Singleton
class PetOverlayController @Inject constructor(
    @ApplicationContext private val context: Context,
    private val arenaRepository: PetArenaRepository,
    private val configurePetArenaUseCase: ConfigurePetArenaUseCase,
) {
    /**
     * @return danh sach PetArenaSpec se duoc spawn (de UI test).
     */
    suspend fun apply(
        settings: HomeSettings,
        unlocked: Set<String>,
        catalog: List<ShimejiCharacter>,
    ): List<PetArenaSpec> {
        val byId = catalog.associateBy { it.id }
        val selected = settings.selectedSlotIds
            .mapIndexedNotNull { idx, id ->
                if (id == null) null
                else byId[id]?.takeIf { unlocked.contains(it.id) }?.let { idx to it }
            }
        val chosen = when {
            !settings.activateEnabled -> emptyList()
            settings.swarmEnabled -> selected
            else -> selected.take(1)
        }
        val specs = chosen.map { (idx, ch) ->
            PetArenaSpec(
                id = PetId("slot_$idx:${ch.id}"),
                assetFolder = "${ch.pack.dirName}/${ch.displayName}",
            )
        }
        val area = arenaRepository.arena.value.playArea
        configurePetArenaUseCase(playArea = area, pets = specs)
        if (settings.activateEnabled && specs.isNotEmpty()) {
            PetOverlayService.start(context)
        } else {
            PetOverlayService.stop(context)
        }
        return specs
    }
}

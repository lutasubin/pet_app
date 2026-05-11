package com.weappsinc.screenpet.feature.home.presentation

import com.weappsinc.screenpet.feature.home.domain.model.HomeSettings
import com.weappsinc.screenpet.feature.home.domain.model.ShimejiCharacter
import com.weappsinc.screenpet.feature.home.domain.model.ShimejiPack
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class HomeUiStateTest {

    private val catalog = listOf(
        ShimejiCharacter("data1:Abbie", "Abbie", ShimejiPack.DATA1, "data1/Abbie/shime1.png"),
        ShimejiCharacter("data2:Lori", "Lori", ShimejiPack.DATA2, "data2/Lori/shime1.png"),
    )

    @Test
    fun slot_empty_picked_locked_phan_loai_dung() {
        val settings = HomeSettings(
            selectedSlotIds = listOf("data1:Abbie", "data2:Lori", null, null, null, null),
            unlockedSlotCount = 2,
        )
        val state = HomeUiState(
            isLoading = false,
            settings = settings,
            catalog = catalog,
            unlockedIds = setOf("data1:Abbie"),
            downloadedIds = setOf("data1:Abbie"),
        )
        val slots = state.slots
        assertTrue(slots[0] is HomeSlotUiModel.Picked)
        assertTrue(slots[1] is HomeSlotUiModel.CharacterLocked)
        assertTrue(slots[2] is HomeSlotUiModel.SlotLocked)
        assertTrue(slots[5] is HomeSlotUiModel.SlotLocked)
    }
}

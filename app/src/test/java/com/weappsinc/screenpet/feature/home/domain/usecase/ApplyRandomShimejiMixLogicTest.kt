package com.weappsinc.screenpet.feature.home.domain.usecase

import com.weappsinc.screenpet.feature.home.domain.model.HomeSettings
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ApplyRandomShimejiMixLogicTest {

    @Test
    fun no_selection_khi_moi_slot_mo_deu_null() {
        val s = HomeSettings(
            selectedSlotIds = List(6) { null },
            unlockedSlotCount = 3,
        )
        assertTrue(ApplyRandomShimejiMixLogic.hasNoSelectionInUnlockedSlots(s))
    }

    @Test
    fun co_chon_roi_thi_khong_tinh_la_chua_chon() {
        val s = HomeSettings(
            selectedSlotIds = listOf("a", null, null, null, null, null),
            unlockedSlotCount = 3,
        )
        assertFalse(ApplyRandomShimejiMixLogic.hasNoSelectionInUnlockedSlots(s))
    }
}

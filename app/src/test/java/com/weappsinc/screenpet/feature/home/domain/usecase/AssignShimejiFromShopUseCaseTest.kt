package com.weappsinc.screenpet.feature.home.domain.usecase

import com.weappsinc.screenpet.feature.home.domain.model.HomeSettings
import org.junit.Assert.assertEquals
import org.junit.Test

class AssignShimejiFromShopUseCaseTest {

    @Test
    fun targetSlot_firstEmptyAmongUnlocked() {
        val s = HomeSettings(
            selectedSlotIds = listOf("a", null, null, null, null, null),
            unlockedSlotCount = 3,
        )
        assertEquals(1, AssignShimejiFromShopUseCase.targetSlotForShopAdd(s))
    }

    @Test
    fun targetSlot_whenAllFilled_returnsZero() {
        val s = HomeSettings(
            selectedSlotIds = listOf("a", "b", "c", null, null, null),
            unlockedSlotCount = 3,
        )
        assertEquals(0, AssignShimejiFromShopUseCase.targetSlotForShopAdd(s))
    }
}

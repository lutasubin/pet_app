package com.weappsinc.screenpet.feature.pet.domain.petsim

import com.weappsinc.screenpet.feature.pet.domain.model.PetInput
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldDefaults
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PetArenaSimulatorTest {

    @Test
    fun tickAll_roundtrip_primary_world_giong_pet_engine() {
        val area = PetPlayArea(400, 600)
        val world = PetWorldDefaults.initial(area)
        val arena = PetArenaState.fromPrimaryWorld(world)
        val afterArena = PetArenaSimulator.advance(arena, PetArenaInput.TickAll(16L)).getOrThrow()
        val afterWorld = afterArena.toPrimaryWorld()
        val direct = com.weappsinc.screenpet.feature.pet.domain.engine.PetEngine.advance(
            world,
            PetInput.Tick(16L),
        ).getOrThrow()
        assertEquals(direct.snapshot.frameIndex, afterWorld.snapshot.frameIndex)
        assertEquals(direct.snapshot.phase, afterWorld.snapshot.phase)
    }

    @Test
    fun tickAll_hai_pet_chay_doc_lap_va_giu_asset_folder() {
        val area = PetPlayArea(400, 600)
        val w1 = PetWorldDefaults.initial(area)
        val w2 = PetWorldDefaults.initial(area).let {
            it.copy(snapshot = it.snapshot.copy(anchorXPx = 50f))
        }
        val arena = PetArenaState(
            playArea = area,
            pets = mapOf(
                PetId("a") to PetEntityState.fromWorld(w1, assetFolder = "data1/Abbie"),
                PetId("b") to PetEntityState.fromWorld(w2, assetFolder = "data2/Lori"),
            ),
        )
        val after = PetArenaSimulator.advance(arena, PetArenaInput.TickAll(16L)).getOrThrow()
        val a = after.pets[PetId("a")]!!
        val b = after.pets[PetId("b")]!!
        assertEquals("data1/Abbie", a.assetFolder)
        assertEquals("data2/Lori", b.assetFolder)
        assertNotEquals(a.snapshot.anchorXPx, b.snapshot.anchorXPx)
        assertTrue("pet a phai di chuyen", a.snapshot.anchorXPx != w1.snapshot.anchorXPx)
    }

    @Test
    fun pointerDown_chi_anh_huong_dung_petId() {
        val area = PetPlayArea(400, 600)
        val w = PetWorldDefaults.initial(area)
        val arena = PetArenaState(
            playArea = area,
            pets = mapOf(
                PetId("a") to PetEntityState.fromWorld(w),
                PetId("b") to PetEntityState.fromWorld(w),
            ),
        )
        val after = PetArenaSimulator.advance(
            arena,
            PetArenaInput.PointerDown(PetId("a"), w.snapshot.anchorXPx, w.snapshot.anchorYPx),
        ).getOrThrow()
        val a = after.pets[PetId("a")]!!
        val b = after.pets[PetId("b")]!!
        assertTrue("pet a phai dang dragged", a.snapshot.isDragging)
        assertTrue("pet b phai khong bi anh huong", !b.snapshot.isDragging)
    }
}

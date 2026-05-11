package com.weappsinc.screenpet.feature.pet.domain.petsim

import com.weappsinc.screenpet.feature.pet.domain.model.PetInput
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldDefaults
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState
import org.junit.Assert.assertEquals
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
}

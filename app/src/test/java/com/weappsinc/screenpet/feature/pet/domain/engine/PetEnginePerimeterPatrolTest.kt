package com.weappsinc.screenpet.feature.pet.domain.engine

import com.weappsinc.screenpet.feature.pet.domain.model.PerimeterPatrolStage
import com.weappsinc.screenpet.feature.pet.domain.model.PetInput
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.model.PetRuntimePhase
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldDefaults
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PetEnginePerimeterPatrolTest {

    @Test
    fun bottom_walk_hits_wall_then_attaches_climb_instead_of_reflect() {
        val area = PetPlayArea(400, 600)
        val maxX = PetBoundsGeometry.maxAnchorX(area)
        val maxY = PetBoundsGeometry.maxAnchorY(area)
        val start = PetWorldDefaults.initial(area).snapshot.copy(
            anchorXPx = maxX - 4f,
            anchorYPx = maxY,
            lookRight = true,
        )
        var w = PetWorldState(area, start)
        repeat(30) {
            w = PetEngine.advance(w, PetInput.Tick(16L)).getOrThrow()
        }
        assertTrue(
            w.snapshot.phase == PetRuntimePhase.WallRight ||
                w.snapshot.phase == PetRuntimePhase.WallLeft,
        )
        assertEquals(PerimeterPatrolStage.AscendFirstThird, w.snapshot.perimeterStage)
    }
}

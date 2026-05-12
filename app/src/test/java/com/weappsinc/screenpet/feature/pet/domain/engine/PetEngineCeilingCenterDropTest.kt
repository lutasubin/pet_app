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

class PetEngineCeilingCenterDropTest {

    private fun ceilingStart(area: PetPlayArea, fromLeft: Boolean) =
        PetWorldDefaults.initial(area).snapshot.copy(
            anchorXPx = if (fromLeft) PetBoundsGeometry.minAnchorX(area) else PetBoundsGeometry.maxAnchorX(area),
            anchorYPx = PetBoundsGeometry.minAnchorY(area),
            lookRight = fromLeft,
            phase = PetRuntimePhase.Ceiling,
            perimeterStage = PerimeterPatrolStage.CeilingWalk,
            perimeterPatrolEnabled = true,
            velocityXPxPerSec = 0f,
            velocityYPxPerSec = 0f,
        )

    private fun runUntilSequence(world: PetWorldState, maxTicks: Int): List<PetRuntimePhase> {
        val seq = mutableListOf(world.snapshot.phase)
        var w = world
        repeat(maxTicks) {
            w = PetEngine.advance(w, PetInput.Tick(16L)).getOrThrow()
            if (seq.last() != w.snapshot.phase) seq += w.snapshot.phase
        }
        return seq
    }

    @Test
    fun ceiling_walk_from_left_drops_at_center_then_walks_again() {
        val area = PetPlayArea(400, 600)
        val w = PetWorldState(area, ceilingStart(area, fromLeft = true))
        val seq = runUntilSequence(w, 400)
        val firstAir = seq.indexOf(PetRuntimePhase.Airborne)
        assertTrue("Pet phai chuyen sang Airborne (roi giua tran)", firstAir >= 0)
        val tail = seq.drop(firstAir)
        assertTrue("Sau khi roi phai co Bouncing", tail.contains(PetRuntimePhase.Bouncing))
        assertTrue("Sau Bouncing phai tro lai GroundMoving", tail.contains(PetRuntimePhase.GroundMoving))
    }

    @Test
    fun ceiling_walk_from_right_drops_at_center_too() {
        val area = PetPlayArea(400, 600)
        val w = PetWorldState(area, ceilingStart(area, fromLeft = false))
        val seq = runUntilSequence(w, 400)
        val firstAir = seq.indexOf(PetRuntimePhase.Airborne)
        assertTrue("Pet phai chuyen sang Airborne (roi giua tran tu phai)", firstAir >= 0)
        val tail = seq.drop(firstAir)
        assertTrue(tail.contains(PetRuntimePhase.GroundMoving))
    }

    @Test
    fun drop_happens_near_center_x() {
        val area = PetPlayArea(400, 600)
        var w = PetWorldState(area, ceilingStart(area, fromLeft = true))
        var dropX = Float.NaN
        repeat(400) {
            val prevPhase = w.snapshot.phase
            w = PetEngine.advance(w, PetInput.Tick(16L)).getOrThrow()
            if (dropX.isNaN() &&
                prevPhase == PetRuntimePhase.Ceiling &&
                w.snapshot.phase == PetRuntimePhase.Airborne
            ) {
                dropX = w.snapshot.anchorXPx
            }
        }
        assertTrue("Phai bat duoc lan roi dau", !dropX.isNaN())
        val centerX = area.widthPx / 2f
        assertTrue("Diem roi (x=$dropX) phai gan giua $centerX", kotlin.math.abs(dropX - centerX) < 4f)
    }
}

package com.weappsinc.screenpet.feature.pet.domain.engine

import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.core.constants.ShimejiFrameCatalog
import com.weappsinc.screenpet.feature.pet.domain.model.PetBorderContact
import com.weappsinc.screenpet.feature.pet.domain.model.PetInput
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.model.PetRuntimePhase
import com.weappsinc.screenpet.feature.pet.domain.model.PetSnapshot
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldDefaults
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PetEngineTest {

    @Test
    fun tick_groundMoving_walk_loops_frame_index() {
        val area = PetPlayArea(400, 600)
        val base = PetWorldDefaults.initial(area)
        val walking = base.snapshot.copy(
            phase = PetRuntimePhase.GroundMoving,
            clipId = ShimejiClipId.Walk,
            frameIndex = 0,
            msAccumulatedInFrame = 0f,
            contact = PetBorderContact(floor = true),
            perimeterPatrolEnabled = false,
        )
        var cur = PetWorldState(area, walking)
        val clipLen = ShimejiFrameCatalog.clip(ShimejiClipId.Walk).frameIndices.size
        repeat(clipLen * 3) {
            cur = PetEngine.advance(cur, PetInput.Tick(6)).getOrThrow()
        }
        assertEquals(0, cur.snapshot.frameIndex % clipLen)
        assertEquals(ShimejiClipId.Walk, cur.snapshot.clipId)
    }

    @Test
    fun tick_airborne_hits_floor_then_idle_or_bounce() {
        val area = PetPlayArea(320, 480)
        val snap = PetSnapshot(
            anchorXPx = 160f,
            anchorYPx = 200f,
            velocityXPxPerSec = 0f,
            velocityYPxPerSec = 900f,
            lookRight = true,
            clipId = ShimejiClipId.Falling,
            frameIndex = 0,
            msAccumulatedInFrame = 0f,
            phase = PetRuntimePhase.Airborne,
            contact = PetBorderContact(),
            isDragging = false,
            dragGrabOffsetXPx = 0f,
            dragGrabOffsetYPx = 0f,
            bouncePhaseRemainingMs = 0L,
            perimeterPatrolEnabled = false,
        )
        var cur = PetWorldState(area, snap)
        repeat(400) {
            cur = PetEngine.advance(cur, PetInput.Tick(16)).getOrThrow()
        }
        assertTrue(
            cur.snapshot.phase == PetRuntimePhase.GroundMoving ||
                cur.snapshot.phase == PetRuntimePhase.GroundIdle ||
                cur.snapshot.phase == PetRuntimePhase.Bouncing,
        )
        assertTrue(cur.snapshot.anchorYPx <= area.heightPx.toFloat() + 1f)
    }

    @Test
    fun tick_clamps_anchor_inside_play_area() {
        val area = PetPlayArea(300, 500)
        val base = PetWorldDefaults.initial(area)
        val out = base.snapshot.copy(anchorXPx = 50_000f, phase = PetRuntimePhase.GroundIdle)
        val cur = PetEngine.advance(PetWorldState(area, out), PetInput.Tick(1)).getOrThrow()
        val maxX = PetBoundsGeometry.maxAnchorX(area)
        assertTrue(cur.snapshot.anchorXPx <= maxX + 0.01f)
    }

    @Test
    fun layout_rejects_too_small_area() {
        val base = PetWorldDefaults.initial(PetPlayArea(400, 600))
        val err = PetEngine.advance(base, PetInput.Layout(PetPlayArea(50, 50))).exceptionOrNull()
        assertTrue(err is com.weappsinc.screenpet.feature.pet.domain.model.PetEngineException)
    }
}

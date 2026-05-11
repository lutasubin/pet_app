package com.weappsinc.screenpet.feature.pet.domain.engine

import com.weappsinc.screenpet.core.constants.ShimejiClipId
import com.weappsinc.screenpet.feature.pet.domain.model.PetBorderContact
import com.weappsinc.screenpet.feature.pet.domain.model.PetInput
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.model.PetRuntimePhase
import com.weappsinc.screenpet.feature.pet.domain.model.PetSnapshot
import com.weappsinc.screenpet.feature.pet.domain.model.PetWorldState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PetEnginePointerReleaseTest {

    @Test
    fun pointerUp_slow_near_floor_sets_sit() {
        val area = PetPlayArea(400, 600)
        val w = draggedWorld(area, 200f, PetBoundsGeometry.maxAnchorY(area) - 10f)
        val out = PetEngine.advance(w, PetInput.PointerUp(0f, 0f)).getOrThrow()
        assertEquals(ShimejiClipId.Sit, out.snapshot.clipId)
        assertEquals(PetRuntimePhase.GroundIdle, out.snapshot.phase)
    }

    @Test
    fun pointerUp_slow_near_floor_downward_velocity_sit_dangle() {
        val area = PetPlayArea(400, 600)
        val w = draggedWorld(area, 200f, PetBoundsGeometry.maxAnchorY(area) - 10f)
        val out = PetEngine.advance(w, PetInput.PointerUp(0f, 200f)).getOrThrow()
        assertEquals(ShimejiClipId.SitDangleLegs, out.snapshot.clipId)
    }

    @Test
    fun pointerUp_slow_near_top_sit_look_up() {
        val area = PetPlayArea(400, 800)
        val minY = PetBoundsGeometry.minAnchorY(area)
        val w = draggedWorld(area, 200f, minY + 80f)
        val out = PetEngine.advance(w, PetInput.PointerUp(0f, 0f)).getOrThrow()
        assertEquals(ShimejiClipId.SitLookUp, out.snapshot.clipId)
        assertEquals(PetRuntimePhase.GroundIdle, out.snapshot.phase)
        assertEquals(minY, out.snapshot.anchorYPx, 0.5f)
    }

    @Test
    fun pointerUp_from_top_perch_drag_to_left_wall_starts_wall_descend() {
        val area = PetPlayArea(400, 800)
        val minY = PetBoundsGeometry.minAnchorY(area)
        val minX = PetBoundsGeometry.minAnchorX(area)
        val snap = PetSnapshot(
            anchorXPx = minX + 40f,
            anchorYPx = minY + 50f,
            velocityXPxPerSec = 0f,
            velocityYPxPerSec = 0f,
            lookRight = true,
            clipId = ShimejiClipId.PinchDragPreview,
            frameIndex = 0,
            msAccumulatedInFrame = 0f,
            phase = PetRuntimePhase.Dragged,
            contact = PetBorderContact(),
            isDragging = true,
            dragGrabOffsetXPx = 0f,
            dragGrabOffsetYPx = 0f,
            bouncePhaseRemainingMs = 0L,
        )
        val w = PetWorldState(
            playArea = area,
            snapshot = PetBoundsGeometry.clampAnchor(snap, area),
            dragAnchorStartXPx = 200f,
            dragAnchorStartYPx = minY + 30f,
        )
        val out = PetEngine.advance(w, PetInput.PointerUp(0f, 0f)).getOrThrow()
        assertEquals(PetRuntimePhase.WallLeft, out.snapshot.phase)
        assertTrue(out.snapshot.wallDescend)
        assertEquals(ShimejiClipId.ClimbWall, out.snapshot.clipId)
    }

    @Test
    fun pointerUp_strong_fling_up_airborne() {
        val area = PetPlayArea(400, 800)
        val w = draggedWorld(area, 200f, 400f)
        val out = PetEngine.advance(w, PetInput.PointerUp(0f, -600f)).getOrThrow()
        assertEquals(PetRuntimePhase.Airborne, out.snapshot.phase)
        assertEquals(ShimejiClipId.Falling, out.snapshot.clipId)
    }

    private fun draggedWorld(area: PetPlayArea, ax: Float, ay: Float): PetWorldState {
        val snap = PetSnapshot(
            anchorXPx = ax,
            anchorYPx = ay,
            velocityXPxPerSec = 0f,
            velocityYPxPerSec = 0f,
            lookRight = true,
            clipId = ShimejiClipId.PinchDragPreview,
            frameIndex = 0,
            msAccumulatedInFrame = 0f,
            phase = PetRuntimePhase.Dragged,
            contact = PetBorderContact(),
            isDragging = true,
            dragGrabOffsetXPx = 0f,
            dragGrabOffsetYPx = 0f,
            bouncePhaseRemainingMs = 0L,
        )
        return PetWorldState(area, PetBoundsGeometry.clampAnchor(snap, area))
    }
}

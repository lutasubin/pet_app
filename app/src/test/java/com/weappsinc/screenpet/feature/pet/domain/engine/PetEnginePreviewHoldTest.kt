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

class PetEnginePreviewHoldTest {

    @Test
    fun previewHold_advances_frame_khong_doi_anchor() {
        val area = PetPlayArea(400, 600)
        val ax = 200f
        val ay = PetBoundsGeometry.maxAnchorY(area)
        val snap = PetSnapshot(
            anchorXPx = ax,
            anchorYPx = ay,
            velocityXPxPerSec = 0f,
            velocityYPxPerSec = 0f,
            lookRight = true,
            clipId = ShimejiClipId.Walk,
            frameIndex = 0,
            msAccumulatedInFrame = 0f,
            phase = PetRuntimePhase.PreviewHold,
            contact = PetBorderContact(floor = true),
            isDragging = false,
            dragGrabOffsetXPx = 0f,
            dragGrabOffsetYPx = 0f,
            bouncePhaseRemainingMs = 0L,
        )
        var world = PetWorldState(area, snap)
        repeat(20) {
            world = PetEngine.advance(world, PetInput.Tick(16)).getOrThrow()
        }
        assertEquals(ax, world.snapshot.anchorXPx, 0.5f)
        assertEquals(ay, world.snapshot.anchorYPx, 0.5f)
        assertTrue(world.snapshot.frameIndex > 0)
    }
}

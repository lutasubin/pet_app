package com.weappsinc.screenpet.core.constants

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ShimejiFrameCatalogTest {

    @Test
    fun catalog_covers_every_clip_id() {
        assertEquals(ShimejiClipId.entries.size, ShimejiFrameCatalog.allClips().size)
    }

    @Test
    fun every_clip_has_valid_frame_range() {
        ShimejiClipId.entries.forEach { id ->
            val clip = ShimejiFrameCatalog.clip(id)
            assertTrue(clip.frameIndices.all { it in ShimejiFrameIndices.MIN..ShimejiFrameIndices.MAX })
        }
    }
}

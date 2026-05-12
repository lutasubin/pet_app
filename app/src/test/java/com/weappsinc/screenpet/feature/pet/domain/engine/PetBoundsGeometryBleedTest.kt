package com.weappsinc.screenpet.feature.pet.domain.engine

import com.weappsinc.screenpet.core.constants.PetSpriteAnchor
import com.weappsinc.screenpet.core.constants.PetSpriteVisualBleed
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import org.junit.Assert.assertEquals
import org.junit.Test

class PetBoundsGeometryBleedTest {

    /** Đảm bảo bleed cho phép pet vượt mép màn hình (để chân/ thân dính sát viền). */
    @Test
    fun maxAnchorY_vuot_mep_man_hinh_bang_bleed_bottom() {
        val area = PetPlayArea(widthPx = 400, heightPx = 800)
        val expected = 800f + PetSpriteVisualBleed.INTO_EDGE_BOTTOM_PX
        assertEquals(expected, PetBoundsGeometry.maxAnchorY(area), 0.001f)
    }

    @Test
    fun minAnchorX_va_maxAnchorX_doi_xung_qua_bleed_trai_phai() {
        val area = PetPlayArea(widthPx = 400, heightPx = 800)
        val minX = PetSpriteAnchor.ANCHOR_X_IN_SPRITE - PetSpriteVisualBleed.INTO_EDGE_LEFT_PX
        val maxX = area.widthPx - (PetSpriteAnchor.SPRITE_WIDTH_PX - PetSpriteAnchor.ANCHOR_X_IN_SPRITE) +
            PetSpriteVisualBleed.INTO_EDGE_RIGHT_PX
        assertEquals(minX.toFloat(), PetBoundsGeometry.minAnchorX(area), 0.001f)
        assertEquals(maxX.toFloat(), PetBoundsGeometry.maxAnchorX(area), 0.001f)
    }
}

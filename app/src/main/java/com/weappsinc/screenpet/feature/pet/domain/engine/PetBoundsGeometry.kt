package com.weappsinc.screenpet.feature.pet.domain.engine

import com.weappsinc.screenpet.core.constants.PetPhysicsConstants
import com.weappsinc.screenpet.core.constants.PetSpriteAnchor
import com.weappsinc.screenpet.core.constants.PetSpriteVisualBleed
import com.weappsinc.screenpet.feature.pet.domain.model.PetBorderContact
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea
import com.weappsinc.screenpet.feature.pet.domain.model.PetSnapshot
import kotlin.math.hypot

object PetBoundsGeometry {
    fun minAnchorX(area: PetPlayArea): Float =
        (PetSpriteAnchor.ANCHOR_X_IN_SPRITE - PetSpriteVisualBleed.INTO_EDGE_LEFT_PX).toFloat()

    fun maxAnchorX(area: PetPlayArea): Float =
        area.widthPx - (PetSpriteAnchor.SPRITE_WIDTH_PX - PetSpriteAnchor.ANCHOR_X_IN_SPRITE).toFloat() +
            PetSpriteVisualBleed.INTO_EDGE_RIGHT_PX.toFloat()

    fun minAnchorY(area: PetPlayArea): Float =
        (PetSpriteAnchor.ANCHOR_Y_IN_SPRITE - PetSpriteVisualBleed.INTO_EDGE_TOP_PX).toFloat()

    fun maxAnchorY(area: PetPlayArea): Float =
        area.heightPx.toFloat() + PetSpriteVisualBleed.INTO_EDGE_BOTTOM_PX.toFloat()

    fun clampAnchor(s: PetSnapshot, area: PetPlayArea): PetSnapshot {
        val nx = s.anchorXPx.coerceIn(minAnchorX(area), maxAnchorX(area))
        val ny = s.anchorYPx.coerceIn(minAnchorY(area), maxAnchorY(area))
        return s.copy(anchorXPx = nx, anchorYPx = ny)
    }

    fun computeContact(s: PetSnapshot, area: PetPlayArea): PetBorderContact {
        val e = 0.6f
        return PetBorderContact(
            floor = s.anchorYPx >= maxAnchorY(area) - e,
            ceiling = s.anchorYPx <= minAnchorY(area) + e,
            wallLeft = s.anchorXPx <= minAnchorX(area) + e,
            wallRight = s.anchorXPx >= maxAnchorX(area) - e,
        )
    }

    fun hitTestPointer(x: Float, y: Float, s: PetSnapshot): Boolean =
        hypot(x - s.anchorXPx, y - s.anchorYPx) <= PetPhysicsConstants.POINTER_HIT_RADIUS_PX
}

package com.weappsinc.screenpet.core.constants

/**
 * Nguong cam ung vuot tha tay (px/s). Y tang xuong man hinh: vy am = nem len.
 */
object PetGestureConstants {
    const val FLING_UP_VY_PX_PER_SEC: Float = -520f
    const val FLING_COMBO_VY_PX_PER_SEC: Float = -280f
    const val FLING_COMBO_SPEED_PX_PER_SEC: Float = 720f
    const val REST_SPEED_PX_PER_SEC: Float = 220f
    const val FLOOR_PROXIMITY_PX: Float = 56f
    const val CEILING_PROXIMITY_PX: Float = 120f
    /** Neo X trong dai nay (tu mép trai/phai) khi tha tay de bat dau bò xuong tu mép trên. */
    const val TOP_TO_WALL_EDGE_RELEASE_PX: Float = 96f
    const val SIT_DANGLE_DOWN_VY_PX_PER_SEC: Float = 140f
    const val RELEASE_VELOCITY_SCALE: Float = 1.12f
}

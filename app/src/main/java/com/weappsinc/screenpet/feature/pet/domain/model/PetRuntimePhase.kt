package com.weappsinc.screenpet.feature.pet.domain.model

/** Pha hanh vi cao cap (FSM). */
enum class PetRuntimePhase {
    Dragged,
    GroundIdle,
    GroundMoving,
    Airborne,
    Bouncing,
    WallLeft,
    WallRight,
    Ceiling,
}

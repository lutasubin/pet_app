package com.weappsinc.screenpet.feature.pet.domain.model

import com.weappsinc.screenpet.core.constants.ShimejiClipId

/**
 * Trang thai pet tai mot thoi diem (bat bien log).
 */
data class PetSnapshot(
    val anchorXPx: Float,
    val anchorYPx: Float,
    val velocityXPxPerSec: Float,
    val velocityYPxPerSec: Float,
    val lookRight: Boolean,
    val clipId: ShimejiClipId,
    val frameIndex: Int,
    val msAccumulatedInFrame: Float,
    val phase: PetRuntimePhase,
    val contact: PetBorderContact,
    val isDragging: Boolean,
    val dragGrabOffsetXPx: Float,
    val dragGrabOffsetYPx: Float,
    val bouncePhaseRemainingMs: Long,
    /** True: dang bò tuong xuong san (tu mép trên keo ra mép). */
    val wallDescend: Boolean = false,
    /** Bat vong lap bam vien (day -> leo 1/3 -> nhay tuong -> ... -> tran -> xuong). */
    val perimeterPatrolEnabled: Boolean = true,
    val perimeterStage: PerimeterPatrolStage = PerimeterPatrolStage.BottomWalk,
)

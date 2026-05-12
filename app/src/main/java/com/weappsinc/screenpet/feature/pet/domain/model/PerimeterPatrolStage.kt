package com.weappsinc.screenpet.feature.pet.domain.model

/** Buoc vong lap bam vien man hinh (day du / zigzag leo tuong). */
enum class PerimeterPatrolStage {
    BottomWalk,
    AscendFirstThird,
    AirCrossAfterFirstThird,
    AscendSecondThird,
    AirCrossAfterSecondThird,
    AscendToTop,
    CeilingWalk,
}

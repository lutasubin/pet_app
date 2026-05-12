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
    /** Bò tuong xuong: 1/3 -> nhay -> 1/3 -> nhay -> ve san. */
    DescendFirstThird,
    AirCrossDescendAfterFirstThird,
    DescendSecondThird,
    AirCrossDescendAfterSecondThird,
    DescendToFloor,
}

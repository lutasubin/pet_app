package com.weappsinc.screenpet.feature.pet.domain.model

/** Bien dang tiep xuc (khong co = dang trong khong gian vung choi). */
data class PetBorderContact(
    val floor: Boolean = false,
    val ceiling: Boolean = false,
    val wallLeft: Boolean = false,
    val wallRight: Boolean = false,
)

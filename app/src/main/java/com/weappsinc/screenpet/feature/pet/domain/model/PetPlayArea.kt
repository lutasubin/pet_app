package com.weappsinc.screenpet.feature.pet.domain.model

/**
 * Vung choi trong app (px). Goc (0,0) goc tren-trai, truc Y huong xuong.
 */
data class PetPlayArea(
    val widthPx: Int,
    val heightPx: Int,
) {
    init {
        require(widthPx > 0 && heightPx > 0) { "PetPlayArea phai co kich thuoc duong" }
    }
}

package com.weappsinc.screenpet.core.constants

/**
 * Mot clip hoat hinh: thu tu frame (index shime 1..46) + mac dinh lap + toc do.
 * Van toc/dieu kien bien giong Shimeji-EE xu ly o ViewModel / tang pet sau.
 */
data class PetFrameClip(
    val frameIndices: List<Int>,
    val loop: Boolean,
    val defaultFrameMs: Int,
) {
    init {
        require(frameIndices.isNotEmpty()) { "Clip can co it nhat mot frame" }
        require(frameIndices.all { it in ShimejiFrameIndices.MIN..ShimejiFrameIndices.MAX }) {
            "Moi chi so phai trong ${ShimejiFrameIndices.MIN}..${ShimejiFrameIndices.MAX}"
        }
        require(defaultFrameMs > 0) { "defaultFrameMs phai > 0" }
    }
}

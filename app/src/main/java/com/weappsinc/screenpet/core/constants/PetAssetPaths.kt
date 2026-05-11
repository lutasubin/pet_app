package com.weappsinc.screenpet.core.constants

/**
 * Duong dan asset pet trong thu muc [android_asset].
 * Anh shime dat tai `assets/img/shime1.png` .. `shime46.png`.
 */
object PetAssetPaths {
    const val SHIME_IMAGE_FOLDER: String = "img"

    /** Chi so file shime1 .. shime46 */
    fun shimeRelativePath(index: Int): String {
        require(index in ShimejiFrameIndices.MIN..ShimejiFrameIndices.MAX) {
            "Chi so shime phai trong ${ShimejiFrameIndices.MIN}..${ShimejiFrameIndices.MAX}"
        }
        return "$SHIME_IMAGE_FOLDER/shime$index.png"
    }
}

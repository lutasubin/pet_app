package com.weappsinc.screenpet.core.constants

/**
 * Duong dan asset pet trong thu muc [android_asset].
 * Anh shime dat tai `assets/img/shime1.png` .. `shime46.png`.
 */
object PetAssetPaths {
    const val SHIME_IMAGE_FOLDER: String = "img"

    /** Chi so file shime1 .. shime46 trong folder mac dinh */
    fun shimeRelativePath(index: Int): String = shimeRelativePath(SHIME_IMAGE_FOLDER, index)

    /** Cho phep custom folder (vi du "data1/Ace") de swarm dung nhieu pet khac nhau. */
    fun shimeRelativePath(folder: String, index: Int): String {
        require(index in ShimejiFrameIndices.MIN..ShimejiFrameIndices.MAX) {
            "Chi so shime phai trong ${ShimejiFrameIndices.MIN}..${ShimejiFrameIndices.MAX}"
        }
        return "$folder/shime$index.png"
    }
}

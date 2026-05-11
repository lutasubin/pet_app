package com.weappsinc.screenpet.core.constants

/** Quy uoc duong dan asset cho catalog Shimeji (data1 + data2). */
object ShimejiCatalogAssetPaths {
    const val DATA1_DIR: String = "data1"
    const val DATA2_DIR: String = "data2"
    const val THUMBNAIL_FRAME: Int = 1

    /** "$packDir/$name/shime$index.png" */
    fun shimePath(packDir: String, name: String, index: Int): String =
        "$packDir/$name/shime$index.png"

    fun thumbnailPath(packDir: String, name: String): String =
        shimePath(packDir, name, THUMBNAIL_FRAME)
}

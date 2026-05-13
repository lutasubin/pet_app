package com.weappsinc.screenpet.feature.home.domain.model

/**
 * Mot Shimeji trong catalog.
 * [id] = "$packDirName:$displayName" - dung lam khoa unique va slot pet id.
 */
data class ShimejiCharacter(
    val id: String,
    val displayName: String,
    val pack: ShimejiPack,
    val thumbnailAssetPath: String,
) {
    /** Thu muc asset tuong doi (vi du `data1/Ace`) cho `shime1.png`..`shime46.png`. */
    fun spriteAssetFolder(): String = "${pack.dirName}/$displayName"

    companion object {
        fun composeId(pack: ShimejiPack, displayName: String): String =
            "${pack.dirName}:$displayName"
    }
}

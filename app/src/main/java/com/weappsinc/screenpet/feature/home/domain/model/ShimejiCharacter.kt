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
    companion object {
        fun composeId(pack: ShimejiPack, displayName: String): String =
            "${pack.dirName}:$displayName"
    }
}

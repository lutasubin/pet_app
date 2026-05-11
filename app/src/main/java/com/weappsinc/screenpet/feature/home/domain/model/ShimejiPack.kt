package com.weappsinc.screenpet.feature.home.domain.model

import com.weappsinc.screenpet.core.constants.ShimejiCatalogAssetPaths

/** Pack tap hop Shimeji theo thu muc asset. */
enum class ShimejiPack(val dirName: String) {
    DATA1(ShimejiCatalogAssetPaths.DATA1_DIR),
    DATA2(ShimejiCatalogAssetPaths.DATA2_DIR),
}

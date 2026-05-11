package com.weappsinc.screenpet.feature.home.data

/** Wrapper de fake AssetManager khi unit test. */
interface AssetDirectoryLister {
    /** Tra ve null hoac empty neu khong co thu muc / khong list duoc. */
    fun list(relativePath: String): Array<String>?
}

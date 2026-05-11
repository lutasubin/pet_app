package com.weappsinc.screenpet.feature.home.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/** Doc thu muc tu Android AssetManager. */
@Singleton
class AndroidAssetDirectoryLister @Inject constructor(
    @ApplicationContext private val context: Context,
) : AssetDirectoryLister {
    override fun list(relativePath: String): Array<String>? =
        runCatching { context.assets.list(relativePath) }.getOrNull()
}

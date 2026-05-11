package com.weappsinc.screenpet.feature.pet.presentation

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.weappsinc.screenpet.core.util.ShimejiAssetPathResolve
import java.util.concurrent.ConcurrentHashMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** Cache bitmap pet de tranh decode lai moi frame, gay flicker. */
object PetSpriteCache {

    private val cache = ConcurrentHashMap<String, ImageBitmap>()

    fun cached(path: String): ImageBitmap? = cache[path]

    suspend fun load(context: Context, path: String): ImageBitmap? {
        cache[path]?.let { return it }
        return withContext(Dispatchers.IO) {
            val resolved = ShimejiAssetPathResolve.existingAssetPath(context, path)
            runCatching {
                context.assets.open(resolved).use { stream ->
                    BitmapFactory.decodeStream(stream)?.asImageBitmap()?.also { bm ->
                        cache[path] = bm
                    }
                }
            }.getOrNull()
        }
    }
}

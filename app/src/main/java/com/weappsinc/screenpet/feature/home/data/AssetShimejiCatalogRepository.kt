package com.weappsinc.screenpet.feature.home.data

import com.weappsinc.screenpet.core.constants.ShimejiCatalogAssetPaths
import com.weappsinc.screenpet.feature.home.domain.model.ShimejiCharacter
import com.weappsinc.screenpet.feature.home.domain.model.ShimejiPack
import com.weappsinc.screenpet.feature.home.domain.repository.ShimejiCatalogRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

/** Doc tu app/assets/data1 + data2 va merge thanh 1 catalog (data1 truoc). Lan sau tra cache (Splash preload). */
@Singleton
class AssetShimejiCatalogRepository @Inject constructor(
    private val lister: AssetDirectoryLister,
) : ShimejiCatalogRepository {

    private val cacheMutex = Mutex()
    private var cached: List<ShimejiCharacter>? = null

    override suspend fun loadAll(): Result<List<ShimejiCharacter>> {
        cached?.let { return Result.success(it) }
        return cacheMutex.withLock {
            cached?.let { return@withLock Result.success(it) }
            val result = withContext(Dispatchers.IO) {
                runCatching {
                    buildList {
                        addAll(loadPack(ShimejiPack.DATA1))
                        addAll(loadPack(ShimejiPack.DATA2))
                    }
                }
            }
            result.onSuccess { cached = it }
            result
        }
    }

    private fun loadPack(pack: ShimejiPack): List<ShimejiCharacter> {
        val names = lister.list(pack.dirName)?.toList().orEmpty().sorted()
        return names.map { name ->
            val rel = "${pack.dirName}/$name"
            val inside = lister.list(rel)?.toList().orEmpty()
            val frame = ShimejiThumbnailPicker.minFrameIndex(inside)
                ?: ShimejiCatalogAssetPaths.THUMBNAIL_FRAME
            ShimejiCharacter(
                id = ShimejiCharacter.composeId(pack, name),
                displayName = name,
                pack = pack,
                thumbnailAssetPath = ShimejiCatalogAssetPaths.shimePath(pack.dirName, name, frame),
            )
        }
    }
}

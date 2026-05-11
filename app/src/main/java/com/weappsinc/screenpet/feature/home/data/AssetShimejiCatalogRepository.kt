package com.weappsinc.screenpet.feature.home.data

import com.weappsinc.screenpet.core.constants.ShimejiCatalogAssetPaths
import com.weappsinc.screenpet.feature.home.domain.model.ShimejiCharacter
import com.weappsinc.screenpet.feature.home.domain.model.ShimejiPack
import com.weappsinc.screenpet.feature.home.domain.repository.ShimejiCatalogRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** Doc tu app/assets/data1 + data2 va merge thanh 1 catalog (data1 truoc). */
@Singleton
class AssetShimejiCatalogRepository @Inject constructor(
    private val lister: AssetDirectoryLister,
) : ShimejiCatalogRepository {

    override suspend fun loadAll(): Result<List<ShimejiCharacter>> = withContext(Dispatchers.IO) {
        runCatching {
            buildList {
                addAll(loadPack(ShimejiPack.DATA1))
                addAll(loadPack(ShimejiPack.DATA2))
            }
        }
    }

    private fun loadPack(pack: ShimejiPack): List<ShimejiCharacter> {
        val names = lister.list(pack.dirName)?.toList().orEmpty().sorted()
        return names.map { name ->
            ShimejiCharacter(
                id = ShimejiCharacter.composeId(pack, name),
                displayName = name,
                pack = pack,
                thumbnailAssetPath = ShimejiCatalogAssetPaths.thumbnailPath(pack.dirName, name),
            )
        }
    }
}

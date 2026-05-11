package com.weappsinc.screenpet.feature.home.data

import com.weappsinc.screenpet.feature.home.domain.model.ShimejiPack
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AssetShimejiCatalogRepositoryTest {

    private class FakeLister(
        private val data1: Array<String>?,
        private val data2: Array<String>?,
    ) : AssetDirectoryLister {
        override fun list(relativePath: String): Array<String>? = when (relativePath) {
            "data1" -> data1
            "data2" -> data2
            else -> emptyArray()
        }
    }

    @Test
    fun merge_data1_truoc_data2_va_sap_xep_alpha() = runTest {
        val lister = FakeLister(
            data1 = arrayOf("Charlie", "Abbie"),
            data2 = arrayOf("Zed", "Lori"),
        )
        val repo = AssetShimejiCatalogRepository(lister)
        val all = repo.loadAll().getOrThrow()
        assertEquals(4, all.size)
        assertEquals("Abbie", all[0].displayName)
        assertEquals(ShimejiPack.DATA1, all[0].pack)
        assertEquals("Charlie", all[1].displayName)
        assertEquals(ShimejiPack.DATA2, all[2].pack)
        assertEquals("Lori", all[2].displayName)
        assertEquals("Zed", all[3].displayName)
    }

    @Test
    fun id_format_dung_pack_va_displayName() = runTest {
        val lister = FakeLister(arrayOf("Abbie"), arrayOf("Lori"))
        val repo = AssetShimejiCatalogRepository(lister)
        val all = repo.loadAll().getOrThrow()
        assertTrue(all.any { it.id == "data1:Abbie" })
        assertTrue(all.any { it.id == "data2:Lori" })
        assertEquals("data1/Abbie/shime1.png", all.first { it.id == "data1:Abbie" }.thumbnailAssetPath)
    }

    @Test
    fun null_assets_tra_ve_rong() = runTest {
        val repo = AssetShimejiCatalogRepository(FakeLister(null, null))
        assertEquals(0, repo.loadAll().getOrThrow().size)
    }
}

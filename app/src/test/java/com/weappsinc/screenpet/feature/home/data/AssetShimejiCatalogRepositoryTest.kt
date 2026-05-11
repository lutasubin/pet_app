package com.weappsinc.screenpet.feature.home.data

import com.weappsinc.screenpet.feature.home.domain.model.ShimejiPack
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AssetShimejiCatalogRepositoryTest {

    private class FakeListerMap(
        private val entries: Map<String, Array<String>?>,
    ) : AssetDirectoryLister {
        override fun list(relativePath: String): Array<String>? =
            entries[relativePath] ?: emptyArray()
    }

    private fun repo(entries: Map<String, Array<String>?>): AssetShimejiCatalogRepository =
        AssetShimejiCatalogRepository(FakeListerMap(entries))

    @Test
    fun merge_data1_truoc_data2_va_sap_xep_alpha() = runTest {
        val r = repo(
            mapOf(
                "data1" to arrayOf("Charlie", "Abbie"),
                "data1/Abbie" to arrayOf("shime1.png"),
                "data1/Charlie" to arrayOf("shime1.png"),
                "data2" to arrayOf("Zed", "Lori"),
                "data2/Zed" to arrayOf("shime1.png"),
                "data2/Lori" to arrayOf("shime1.png"),
            ),
        )
        val all = r.loadAll().getOrThrow()
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
        val r = repo(
            mapOf(
                "data1" to arrayOf("Abbie"),
                "data1/Abbie" to arrayOf("shime1.png"),
                "data2" to arrayOf("Lori"),
                "data2/Lori" to arrayOf("shime1.png"),
            ),
        )
        val all = r.loadAll().getOrThrow()
        assertTrue(all.any { it.id == "data1:Abbie" })
        assertTrue(all.any { it.id == "data2:Lori" })
        assertEquals("data1/Abbie/shime1.png", all.first { it.id == "data1:Abbie" }.thumbnailAssetPath)
    }

    @Test
    fun thumbnail_chon_shime_so_nho_nhat_khi_thieu_shime1() = runTest {
        val r = repo(
            mapOf(
                "data1" to arrayOf("Zoro"),
                "data1/Zoro" to arrayOf("shime4.png", "shime9.png", "2", "walk"),
            ),
        )
        val z = r.loadAll().getOrThrow().single()
        assertEquals("data1:Zoro", z.id)
        assertEquals("data1/Zoro/shime4.png", z.thumbnailAssetPath)
    }

    @Test
    fun null_assets_tra_ve_rong() = runTest {
        val r = repo(mapOf("data1" to null, "data2" to null))
        assertEquals(0, r.loadAll().getOrThrow().size)
    }

    @Test
    fun lan_hai_loadAll_dung_cache_khong_doc_lister_them() = runTest {
        var listCalls = 0
        val lister = object : AssetDirectoryLister {
            override fun list(relativePath: String): Array<String>? {
                listCalls++
                return when (relativePath) {
                    "data1" -> arrayOf("A")
                    "data1/A" -> arrayOf("shime1.png")
                    "data2" -> arrayOf("B")
                    "data2/B" -> arrayOf("shime1.png")
                    else -> emptyArray()
                }
            }
        }
        val repo = AssetShimejiCatalogRepository(lister)
        repo.loadAll().getOrThrow()
        val afterFirst = listCalls
        repo.loadAll().getOrThrow()
        assertEquals(afterFirst, listCalls)
        assertTrue(afterFirst >= 4)
    }
}

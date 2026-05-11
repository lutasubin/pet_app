package com.weappsinc.screenpet.core.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ShimejiAssetPathResolveTest {

    @Test
    fun shime_thuong_sang_Shime_S_hoa() {
        assertEquals(
            "data1/Boyfriend/Shime1.png",
            ShimejiAssetPathResolve.uppercaseShimeVariant("data1/Boyfriend/shime1.png"),
        )
    }

    @Test
    fun ten_thu_muc_co_khoang_trang() {
        assertEquals(
            "data1/Mitsuri Kanroji/Shime10.png",
            ShimejiAssetPathResolve.uppercaseShimeVariant("data1/Mitsuri Kanroji/shime10.png"),
        )
    }

    @Test
    fun khong_phan_hoi_neu_khong_dung_mau_thu_muc_file() {
        assertNull(ShimejiAssetPathResolve.uppercaseShimeVariant("shime1.png"))
        assertNull(ShimejiAssetPathResolve.uppercaseShimeVariant("data1/Boyfriend/actions.xml"))
    }

    @Test
    fun shime_thuong_sang_SHIME_toan_hoa() {
        assertEquals(
            "data1/Boyfriend/SHIME1.png",
            ShimejiAssetPathResolve.allCapsShimeVariant("data1/Boyfriend/shime1.png"),
        )
    }

    @Test
    fun expand_co_ca_duoi_PNG_hoa_cho_Fundy() {
        val c = ShimejiAssetPathResolve.expandAssetPathCandidates("data2/Fundy/shime1.png")
        assertTrue(c.contains("data2/Fundy/shime1.png"))
        assertTrue(c.contains("data2/Fundy/shime1.PNG"))
    }

    @Test
    fun expand_du_PNG_cho_BadBoyHalo() {
        val c = ShimejiAssetPathResolve.expandAssetPathCandidates("data2/BadBoyHalo/shime1.png")
        assertTrue(c.contains("data2/BadBoyHalo/shime1.PNG"))
    }
}

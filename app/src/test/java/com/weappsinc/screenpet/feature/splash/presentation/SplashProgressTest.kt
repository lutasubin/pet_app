package com.weappsinc.screenpet.feature.splash.presentation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SplashProgressTest {

    @Test
    fun tang_deu_theo_timeline_khi_chua_qua_min() {
        assertEquals(0f, computeSplashProgress(0f, catalogReady = false, elapsedPastMin = 0), 0.001f)
        assertEquals(0.485f, computeSplashProgress(0.5f, catalogReady = false, elapsedPastMin = 0), 0.001f)
        assertEquals(0.97f, computeSplashProgress(1f, catalogReady = false, elapsedPastMin = 0), 0.001f)
    }

    @Test
    fun catalog_xong_du_thoi_gian_ve_100() {
        assertEquals(1f, computeSplashProgress(1f, catalogReady = true, elapsedPastMin = 0), 0.001f)
    }

    @Test
    fun catalog_xong_som_van_theo_timeline_khong_ve_100() {
        val p = computeSplashProgress(0.4f, catalogReady = true, elapsedPastMin = 0)
        assertEquals(0.388f, p, 0.02f)
        assertTrue(p < 0.9f)
    }

    @Test
    fun qua_min_catalog_chua_xong_nhich_cham() {
        val p0 = computeSplashProgress(1f, catalogReady = false, elapsedPastMin = 0)
        assertEquals(0.97f, p0, 0.001f)
        val p1 = computeSplashProgress(1f, catalogReady = false, elapsedPastMin = 6000L)
        assertTrue(p1 > p0)
        assertTrue(p1 <= 0.99f)
    }
}

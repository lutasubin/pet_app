package com.weappsinc.screenpet.feature.home.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ShimejiThumbnailPickerTest {

    @Test
    fun lay_so_frame_nho_nhat() {
        assertEquals(
            4,
            ShimejiThumbnailPicker.minFrameIndex(
                listOf("shime9.png", "shime4.png", "2"),
            ),
        )
    }

    @Test
    fun khong_phan_biet_hoa_thuong() {
        assertEquals(2, ShimejiThumbnailPicker.minFrameIndex(listOf("Shime10.png", "SHIME2.png")))
    }

    @Test
    fun khong_co_file_shime() {
        assertNull(ShimejiThumbnailPicker.minFrameIndex(listOf("readme.txt", "2")))
    }

    @Test
    fun nhan_dien_duoi_PNG_hoa() {
        assertEquals(1, ShimejiThumbnailPicker.minFrameIndex(listOf("shime1.PNG", "shime2.PNG")))
    }
}

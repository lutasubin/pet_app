package com.weappsinc.screenpet.feature.home.presentation

import com.weappsinc.screenpet.ui.theme.HomeTokens
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SlotPetDisplayNameAbbreviateTest {

    @Test
    fun ngan_giu_nguyen() {
        assertEquals("Abbie", abbreviateSlotPetDisplayName("Abbie"))
        assertEquals("Hatsune Miku", abbreviateSlotPetDisplayName("Hatsune Miku"))
    }

    @Test
    fun nhieu_tu_dai_dung_chu_cai_dau() {
        val raw = "Super Ultra Mega Pet Name Extra Long"
        val out = abbreviateSlotPetDisplayName(raw)
        assertEquals("SUMPNEL", out)
        assertTrue(out.length <= HomeTokens.SlotPetNameMaxDisplayChars)
    }

    @Test
    fun rat_nhieu_tu_cat_initials() {
        val words = (1..20).joinToString(" ") { "W$it" }
        val out = abbreviateSlotPetDisplayName(words)
        assertEquals(HomeTokens.SlotPetNameMaxDisplayChars, out.length)
    }

    @Test
    fun mot_tu_dai_cat_va_ellipsis() {
        val raw = "Supercalifragilisticexpialidocious"
        val out = abbreviateSlotPetDisplayName(raw)
        assertTrue(out.endsWith("…"))
        assertEquals(HomeTokens.SlotPetNameMaxDisplayChars, out.length)
    }
}

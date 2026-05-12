package com.weappsinc.screenpet.core.util

import android.view.WindowManager
import com.weappsinc.screenpet.feature.pet.domain.model.PetPlayArea

/** Lay kich thuoc man hinh thuc cho engine pet (overlay / vung choi). */
object PetPlayAreaFromWindow {

    /**
     * [WindowManager.getMaximumWindowMetrics] lon hon hoac bang currentWindowMetrics,
     * giup neo pet sat hon voi bien vat ly (thanh he thong / gesture).
     */
    fun resolve(wm: WindowManager): PetPlayArea {
        val b = wm.maximumWindowMetrics.bounds
        val w = b.width().coerceAtLeast(1)
        val h = b.height().coerceAtLeast(1)
        return PetPlayArea(widthPx = w, heightPx = h)
    }
}

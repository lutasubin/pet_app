package com.weappsinc.screenpet.feature.settings.presentation

import android.app.LocaleManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.LocaleList
import androidx.activity.ComponentActivity

/**
 * Doi locale ung dung. Tu API 33+ chi can set [LocaleManager.applicationLocales]
 * — he thong se tu recreate Activity. Khong tu goi [ComponentActivity.recreate]
 * de tranh recreate 2 lan (gay nhay den).
 */
object AppLocaleApplier {
    fun applyAndRecreate(activity: ComponentActivity, tag: String) {
        // Dat nen cua so trang truoc khi recreate de tranh khoanh khac den.
        activity.window.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        if (Build.VERSION.SDK_INT >= 33) {
            val lm = activity.getSystemService(LocaleManager::class.java)
            if (lm != null) {
                lm.applicationLocales = when (tag) {
                    "system" -> LocaleList.getEmptyLocaleList()
                    else -> LocaleList.forLanguageTags(tag)
                }
                return
            }
        }
        activity.recreate()
    }
}

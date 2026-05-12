package com.weappsinc.screenpet.feature.settings.presentation

import android.app.LocaleManager
import android.os.Build
import android.os.LocaleList
import androidx.activity.ComponentActivity

/** Ap dung locale he thong (API 33+) va recreate Activity. */
object AppLocaleApplier {
    fun applyAndRecreate(activity: ComponentActivity, tag: String) {
        if (Build.VERSION.SDK_INT >= 33) {
            val lm = activity.getSystemService(LocaleManager::class.java) ?: return
            lm.applicationLocales = when (tag) {
                "system" -> LocaleList.getEmptyLocaleList()
                else -> LocaleList.forLanguageTags(tag)
            }
        }
        activity.recreate()
    }
}

package com.weappsinc.screenpet.feature.settings.presentation

import android.app.LocaleManager
import android.os.Build
import android.os.LocaleList
import androidx.activity.ComponentActivity

/**
 * Doi locale ung dung. Tu API 33+ chi can set [LocaleManager.applicationLocales].
 *
 * Manifest cua MainActivity da khai bao
 * `android:configChanges="locale|layoutDirection"` → he thong KHONG recreate Activity,
 * chi goi onConfigurationChanged. Compose `LocalConfiguration` se tu cap nhat va
 * `stringResource` recompose voi chu moi → khong co khoanh khac tao/huy window
 * (truoc day chinh la nguon nhay den).
 */
object AppLocaleApplier {
    fun applyAndRecreate(activity: ComponentActivity, tag: String) {
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
        // Fallback cho thiet bi < 33 (hien minSdk=36 nen khong vao day).
        activity.recreate()
    }
}

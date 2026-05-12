package com.weappsinc.screenpet.feature.settings.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ShareCompat

object SettingsExternalActions {
    fun openRateApp(context: Context) {
        val pkg = context.packageName
        val market = Uri.parse("market://details?id=$pkg")
        val web = Uri.parse("https://play.google.com/store/apps/details?id=$pkg")
        val intent = Intent(Intent.ACTION_VIEW, market).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent)
        } catch (_: Exception) {
            context.startActivity(Intent(Intent.ACTION_VIEW, web).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }

    fun openShareApp(context: Context, message: String) {
        ShareCompat.IntentBuilder(context)
            .setType("text/plain")
            .setText(message)
            .startChooser()
    }

    fun openPrivacyPolicy(context: Context, url: String) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
        )
    }
}

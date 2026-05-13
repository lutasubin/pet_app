package com.weappsinc.screenpet.feature.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.core.constants.SettingsAssetPaths
import com.weappsinc.screenpet.feature.home.presentation.SvgAssetIcon
import com.weappsinc.screenpet.feature.settings.domain.model.AppSettings
import com.weappsinc.screenpet.ui.theme.HomeTokens

@Composable
fun SettingsGeneralBlock(
    settings: AppSettings,
    onLanguageClick: () -> Unit,
    onRateClick: () -> Unit,
    onShareClick: () -> Unit,
    onPrivacyClick: () -> Unit,
) {
    SettingsGeneralCard(title = stringResource(R.string.settings_section_general)) {
        val langLabel = when (settings.localeTag) {
            AppSettings.LOCALE_SYSTEM -> stringResource(R.string.settings_language_system)
            "en", "en-GB", "en-US" -> stringResource(R.string.settings_language_en)
            "vi" -> stringResource(R.string.settings_language_vi)
            "pt" -> "Português"
            "es-ES" -> "Español"
            "ja-JP" -> "日本語"
            "ko-KR" -> "한국어"
            "de" -> "Deutsch"
            "id" -> "Indonesia"
            "hi" -> "हिन्दी"
            "ar" -> "العربية"
            "tr" -> "Türkçe"
            else -> settings.localeTag
        }
        GeneralRow(
            path = SettingsAssetPaths.LANGUAGE_RELATIVE,
            title = stringResource(R.string.settings_language_title),
            value = langLabel,
            onClick = onLanguageClick,
        )
        GeneralRow(
            path = SettingsAssetPaths.RATE_RELATIVE,
            title = stringResource(R.string.settings_rate_title),
            value = null,
            onClick = onRateClick,
        )
        GeneralRow(
            path = SettingsAssetPaths.SHARE_RELATIVE,
            title = stringResource(R.string.settings_share_title),
            value = null,
            onClick = onShareClick,
        )
        GeneralRow(
            path = SettingsAssetPaths.POLICY_RELATIVE,
            title = stringResource(R.string.settings_policy_title),
            value = null,
            onClick = onPrivacyClick,
        )
    }
}

@Composable
private fun SettingsGeneralCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(16.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = HomeTokens.SwarmTitle,
        )
        Spacer(Modifier.height(12.dp))
        content()
    }
}

@Composable
private fun GeneralRow(
    path: String,
    title: String,
    value: String?,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        SvgAssetIcon(
            assetRelativePath = path,
            contentDescription = title,
            modifier = Modifier.size(40.dp),
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = HomeTokens.SwarmTitle,
            modifier = Modifier.weight(1f),
        )
        if (value != null) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = HomeTokens.NavActive,
            )
        }
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = HomeTokens.NavInactive,
        )
    }
}

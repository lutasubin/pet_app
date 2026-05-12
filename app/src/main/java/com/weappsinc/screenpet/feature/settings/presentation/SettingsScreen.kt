package com.weappsinc.screenpet.feature.settings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.feature.home.presentation.HomeTitleText
import com.weappsinc.screenpet.feature.settings.domain.model.AppSettings
import com.weappsinc.screenpet.ui.theme.HomeTokens

@Composable
fun SettingsScreen(
    settings: AppSettings,
    onGhostChanged: (Boolean) -> Unit,
    onSizeChangeFinished: (Float) -> Unit,
    onSpeedChangeFinished: (Float) -> Unit,
    onLanguageClick: () -> Unit,
    onRateApp: () -> Unit,
    onShareApp: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    onOpenDevMenu: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize(), color = HomeTokens.Background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            HomeTitleText(
                text = stringResource(R.string.home_title),
                useGradient = true,
            )
            SettingsAnimationBlock(
                settings = settings,
                onGhostChanged = onGhostChanged,
                onSizeChangeFinished = onSizeChangeFinished,
                onSpeedChangeFinished = onSpeedChangeFinished,
            )
            SettingsGeneralBlock(
                settings = settings,
                onLanguageClick = onLanguageClick,
                onRateClick = onRateApp,
                onShareClick = onShareApp,
                onPrivacyClick = onPrivacyPolicy,
            )
        }
    }
}

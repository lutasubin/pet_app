package com.weappsinc.screenpet.feature.settings.presentation

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weappsinc.screenpet.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SettingsRoute(
    onOpenDevMenu: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val settings by viewModel.uiState.collectAsStateWithLifecycle()
    val activity = LocalContext.current as ComponentActivity
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var languageOpen by remember { mutableStateOf(false) }
    var localeApplying by remember { mutableStateOf(false) }
    val shareMessage = stringResource(R.string.settings_share_message, context.packageName)

    Box(modifier) {
        SettingsScreen(
            settings = settings,
            onGhostChanged = viewModel::onGhostModeChanged,
            onSizeChangeFinished = viewModel::onAnimationSizeChangeFinished,
            onSpeedChangeFinished = viewModel::onAnimationSpeedChangeFinished,
            onLanguageClick = { languageOpen = true },
            onRateApp = { SettingsExternalActions.openRateApp(context) },
            onShareApp = { SettingsExternalActions.openShareApp(context, shareMessage) },
            onPrivacyPolicy = {
                SettingsExternalActions.openPrivacyPolicy(
                    context,
                    context.getString(R.string.settings_policy_url_placeholder),
                )
            },
            onOpenDevMenu = onOpenDevMenu,
        )
        if (languageOpen) {
            SettingsLanguageBottomSheet(
                selectedTag = settings.localeTag,
                onDismiss = { languageOpen = false },
                onSelectTag = { tag ->
                    scope.launch {
                        localeApplying = true
                        languageOpen = false
                        // Cho phep frame loading render truoc khi recreate Activity.
                        delay(220)
                        viewModel.persistLocaleTag(tag)
                        AppLocaleApplier.applyAndRecreate(activity, tag)
                    }
                },
            )
        }
        if (localeApplying) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xCCFFFFFF))
                    .pointerInput(Unit) { /* nuot moi cham trong khi dang doi locale */ },
                contentAlignment = Alignment.Center,
            ) {
                GradientCircularLoading()
            }
        }
    }
}

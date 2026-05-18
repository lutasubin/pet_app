package com.weappsinc.screenpet.feature.settings.presentation

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
    var rateOpen by remember { mutableStateOf(false) }
    val savedRating by viewModel.savedRatingStars.collectAsStateWithLifecycle()
    val shareMessage = stringResource(R.string.settings_share_message, context.packageName)

    Box(modifier) {
        SettingsScreen(
            settings = settings,
            onGhostChanged = viewModel::onGhostModeChanged,
            onSizeChangeFinished = viewModel::onAnimationSizeChangeFinished,
            onSpeedChangeFinished = viewModel::onAnimationSpeedChangeFinished,
            onLanguageClick = { languageOpen = true },
            onRateApp = { rateOpen = true },
            onShareApp = { SettingsExternalActions.openShareApp(context, shareMessage) },
            onPrivacyPolicy = {
                SettingsExternalActions.openPrivacyPolicy(
                    context,
                    context.getString(R.string.settings_policy_url_placeholder),
                )
            },
            onOpenDevMenu = onOpenDevMenu,
        )
        if (rateOpen) {
            SettingsRateBottomSheet(
                initialStars = savedRating ?: 0,
                onDismiss = { rateOpen = false },
                onSubmit = { stars ->
                    viewModel.onRatingSubmitted(stars)
                    rateOpen = false
                },
            )
        }
        if (languageOpen) {
            SettingsLanguageBottomSheet(
                selectedTag = settings.localeTag,
                onDismiss = { languageOpen = false },
                onSelectTag = { tag ->
                    if (tag == settings.localeTag) {
                        languageOpen = false
                        return@SettingsLanguageBottomSheet
                    }
                    // Overlay (trong sheet + root) hien NGAY o frame ke tiep.
                    AppLocaleTransitionGate.begin()
                    scope.launch {
                        // Cho overlay fade-in xong (~140ms > 120ms tween) de che hoan toan
                        // truoc khi cau hinh locale doi -> tranh thay chu cu loe ra.
                        delay(140)
                        viewModel.persistLocaleTag(tag)
                        // configChanges=locale -> system goi onConfigurationChanged,
                        // Compose recompose tat ca stringResource. Khong recreate, khong nhay.
                        AppLocaleApplier.applyAndRecreate(activity, tag)
                        // Cho recompose xong roi moi dong sheet (overlay van dang che).
                        delay(120)
                        languageOpen = false
                    }
                },
            )
        }
    }
}

package com.weappsinc.screenpet.feature.onboarding.presentation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FirstRunLanguageRoute(
    activity: ComponentActivity,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FirstRunLanguageViewModel = hiltViewModel(),
) {
    var selectedTag by rememberSaveable { mutableStateOf("en-US") }
    FirstRunLanguageScreen(
        selectedTag = selectedTag,
        onSelectTag = { selectedTag = it },
        onContinue = { viewModel.confirmLocale(activity, selectedTag, onFinish) },
        modifier = modifier,
    )
}

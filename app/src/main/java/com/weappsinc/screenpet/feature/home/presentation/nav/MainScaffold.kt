package com.weappsinc.screenpet.feature.home.presentation.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.weappsinc.screenpet.feature.home.presentation.HomeRoute

@Composable
fun MainScaffold(
    devMenuContent: @Composable () -> Unit,
) {
    var selected by rememberSaveable { mutableStateOf(MainTab.Home) }
    var devOpen by remember { mutableStateOf(false) }
    Scaffold(
        bottomBar = {
            HomeBottomNav(
                selected = selected,
                onSelected = {
                    selected = it
                    if (it != MainTab.Setting) devOpen = false
                },
            )
        },
    ) { padding ->
        when (selected) {
            MainTab.Home -> HomeRoute(modifier = Modifier.padding(padding))
            MainTab.Shop -> ShopRoute(modifier = Modifier.padding(padding))
            MainTab.Setting -> if (devOpen) {
                devMenuContent()
            } else {
                SettingsPlaceholderScreen(
                    onOpenDevMenu = { devOpen = true },
                    modifier = Modifier.padding(padding),
                )
            }
        }
    }
}

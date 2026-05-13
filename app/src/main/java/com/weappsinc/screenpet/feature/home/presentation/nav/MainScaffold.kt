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
import com.weappsinc.screenpet.feature.settings.presentation.SettingsRoute

@Composable
fun MainScaffold(
    devMenuContent: @Composable () -> Unit,
) {
    var selected by rememberSaveable { mutableStateOf(MainTab.Home) }
    var pendingShopCharacterId by remember { mutableStateOf<String?>(null) }
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
            MainTab.Home -> HomeRoute(
                modifier = Modifier.padding(padding),
                pendingShopCharacterId = pendingShopCharacterId,
                onConsumedPendingShopSelection = { pendingShopCharacterId = null },
            )
            MainTab.Shop -> ShopRoute(
                modifier = Modifier.padding(padding),
                onSelectPetGoHome = { id ->
                    pendingShopCharacterId = id
                    selected = MainTab.Home
                },
            )
            MainTab.Setting -> if (devOpen) {
                devMenuContent()
            } else {
                SettingsRoute(
                    onOpenDevMenu = { devOpen = true },
                    modifier = Modifier.padding(padding),
                )
            }
        }
    }
}

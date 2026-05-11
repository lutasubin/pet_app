package com.weappsinc.screenpet.feature.home.presentation.nav

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.ui.graphics.vector.ImageVector
import com.weappsinc.screenpet.R

/** Tab cua bottom navigation. */
enum class MainTab(
    @StringRes val titleRes: Int,
    val icon: ImageVector,
) {
    Home(R.string.home_tab_home, Icons.Filled.Home),
    Shop(R.string.home_tab_shop, Icons.Filled.Storefront),
    Setting(R.string.home_tab_setting, Icons.Filled.Settings),
}

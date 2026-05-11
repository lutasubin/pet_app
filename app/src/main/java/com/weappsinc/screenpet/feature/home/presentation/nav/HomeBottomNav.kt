package com.weappsinc.screenpet.feature.home.presentation.nav

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.ui.theme.HomeTokens

@Composable
fun HomeBottomNav(
    selected: MainTab,
    onSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = Color.White,
        tonalElevation = 0.dp,
    ) {
        MainTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = selected == tab,
                onClick = { onSelected(tab) },
                icon = { Icon(tab.icon, contentDescription = null) },
                label = { Text(stringResource(tab.titleRes)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = HomeTokens.NavActive,
                    selectedTextColor = HomeTokens.NavActive,
                    unselectedIconColor = HomeTokens.NavInactive,
                    unselectedTextColor = HomeTokens.NavInactive,
                    indicatorColor = Color.Transparent,
                ),
            )
        }
    }
}

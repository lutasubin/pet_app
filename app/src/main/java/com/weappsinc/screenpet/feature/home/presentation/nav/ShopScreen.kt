package com.weappsinc.screenpet.feature.home.presentation.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.feature.home.presentation.HomeTitleText
import com.weappsinc.screenpet.ui.theme.HomeTokens

@Composable
fun ShopScreen(
    state: ShopUiState,
    onQueryChange: (String) -> Unit,
    onUnlock: (String) -> Unit,
    onDownload: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize(), color = HomeTokens.Background) {
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                HomeTitleText(text = stringResource(R.string.home_title))
                ShopSearchBar(
                    value = state.query,
                    onValueChange = onQueryChange,
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(state.visibleCatalog, key = { it.id }) { ch ->
                        ShopPetCard(
                            character = ch,
                            unlocked = ch.id in state.unlockedIds,
                            downloaded = ch.id in state.downloadedIds,
                            onUnlock = { onUnlock(ch.id) },
                            onDownload = { onDownload(ch.id) },
                        )
                    }
                }
            }
            if (state.downloadingId != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.25f)),
                )
                ShopDownloadingDialog(
                    progress = state.downloadProgress,
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}

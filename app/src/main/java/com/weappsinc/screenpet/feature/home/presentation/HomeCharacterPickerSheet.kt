package com.weappsinc.screenpet.feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.core.constants.HomeAssetPaths
import com.weappsinc.screenpet.feature.home.domain.model.ShimejiCharacter
import com.weappsinc.screenpet.ui.theme.HomeTokens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeCharacterPickerSheet(
    slot: Int,
    catalog: List<ShimejiCharacter>,
    unlockedIds: Set<String>,
    onDismiss: () -> Unit,
    onSelect: (Int, String?) -> Unit,
    onUnlock: (String) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(560.dp)
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = stringResource(R.string.home_picker_title),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 12.dp),
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(catalog, key = { it.id }) { ch ->
                    PickerCharacterCell(
                        character = ch,
                        unlocked = ch.id in unlockedIds,
                        onPick = { onSelect(slot, ch.id) },
                        onUnlock = { onUnlock(ch.id) },
                    )
                }
            }
        }
    }
}

@Composable
private fun PickerCharacterCell(
    character: ShimejiCharacter,
    unlocked: Boolean,
    onPick: () -> Unit,
    onUnlock: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(if (unlocked) Color.White else HomeTokens.LockTile)
            .clickable { if (unlocked) onPick() else onUnlock() },
        contentAlignment = Alignment.Center,
    ) {
        AssetImage(
            assetRelativePath = character.thumbnailAssetPath,
            contentDescription = character.displayName,
            modifier = Modifier.fillMaxSize().padding(4.dp),
        )
        if (!unlocked) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    SvgAssetIcon(
                        assetRelativePath = HomeAssetPaths.UNLOCK_ICON_RELATIVE,
                        contentDescription = stringResource(R.string.home_lock_cd),
                        modifier = Modifier.fillMaxHeight(0.25f),
                    )
                    Text(
                        text = stringResource(R.string.home_unlock),
                        color = HomeTokens.LockIcon,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}

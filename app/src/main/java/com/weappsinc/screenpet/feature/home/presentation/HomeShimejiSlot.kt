package com.weappsinc.screenpet.feature.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.ui.theme.HomeTokens

@Composable
fun HomeShimejiSlot(
    model: HomeSlotUiModel,
    onClick: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .homeShimejiSlotSurface(model),
    ) {
        when (model) {
            is HomeSlotUiModel.Empty ->
                Box(Modifier.fillMaxSize().clickable(onClick = onClick), contentAlignment = Alignment.Center) {
                    HomeEmptyAddSlotContent()
                }
            is HomeSlotUiModel.Picked ->
                PickedSlotContent(model = model, onClick = onClick, onRemove = onRemove)
            is HomeSlotUiModel.CharacterLocked ->
                Box(Modifier.fillMaxSize().clickable(onClick = onClick), contentAlignment = Alignment.Center) {
                    HomeUnlockSlotContent(R.string.home_slot_character_pending)
                }
            is HomeSlotUiModel.SlotLocked ->
                Box(Modifier.fillMaxSize().clickable(onClick = onClick), contentAlignment = Alignment.Center) {
                    HomeUnlockSlotContent(R.string.home_unlock)
                }
        }
    }
}

@Composable
private fun PickedSlotContent(
    model: HomeSlotUiModel.Picked,
    onClick: () -> Unit,
    onRemove: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 10.dp)
                .clickable(onClick = onClick),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AssetImage(
                assetRelativePath = model.thumbnailAssetPath,
                contentDescription = stringResource(R.string.home_thumbnail_cd),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 4.dp),
            )
            Text(
                text = abbreviateSlotPetDisplayName(model.name),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B2740),
                maxLines = 2,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(R.string.home_slot_remove_cd),
            tint = HomeTokens.SwitchCheckedTrack,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(6.dp)
                .size(26.dp)
                .clickable(onClick = onRemove),
        )
    }
}

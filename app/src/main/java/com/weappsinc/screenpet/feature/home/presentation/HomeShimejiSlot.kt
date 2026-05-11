package com.weappsinc.screenpet.feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.core.constants.HomeAssetPaths
import com.weappsinc.screenpet.ui.theme.HomeTokens

@Composable
fun HomeShimejiSlot(
    model: HomeSlotUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(HomeShimejiSlotShape)
            .homeShimejiSlotDecoration(model)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        when (model) {
            is HomeSlotUiModel.Empty -> EmptySlotContent()
            is HomeSlotUiModel.Picked -> AssetImage(
                assetRelativePath = model.thumbnailAssetPath,
                contentDescription = stringResource(R.string.home_thumbnail_cd),
                modifier = Modifier.fillMaxSize().padding(8.dp),
            )
            is HomeSlotUiModel.CharacterLocked -> LockedSlotContent(R.string.home_slot_character_pending)
            is HomeSlotUiModel.SlotLocked -> LockedSlotContent(R.string.home_unlock)
        }
    }
}

@Composable
private fun EmptySlotContent() {
    Box(
        modifier = Modifier
            .size(52.dp)
            .clip(CircleShape)
            .background(HomeTokens.SlotEmptyCircleFill),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = stringResource(R.string.home_add_shimeji_cd),
            tint = HomeTokens.NavActive,
            modifier = Modifier.size(28.dp),
        )
    }
}

@Composable
private fun LockedSlotContent(@StringRes labelRes: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.size(46.dp).clip(CircleShape).background(Color.White),
            contentAlignment = Alignment.Center,
        ) {
            SvgAssetIcon(
                assetRelativePath = HomeAssetPaths.UNLOCK_ICON_RELATIVE,
                contentDescription = stringResource(R.string.home_lock_cd),
                modifier = Modifier.size(22.dp),
                tint = HomeTokens.LockIcon,
            )
        }
        Text(
            text = stringResource(labelRes),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Medium,
                color = HomeTokens.SlotLockedLabel,
            ),
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}

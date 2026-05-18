package com.weappsinc.screenpet.feature.settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.ui.theme.RateSheetTokens

@Composable
fun SettingsRateStarsRow(
    selectedStars: Int,
    onSelectStars: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
    ) {
        for (index in 1..5) {
            val filled = index <= selectedStars
            Icon(
                imageVector = if (filled) Icons.Filled.Star else Icons.Outlined.StarOutline,
                contentDescription = stringResource(R.string.settings_rate_star_cd, index),
                tint = if (filled) RateSheetTokens.StarFilled else RateSheetTokens.StarEmpty,
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onSelectStars(index) },
            )
        }
    }
}

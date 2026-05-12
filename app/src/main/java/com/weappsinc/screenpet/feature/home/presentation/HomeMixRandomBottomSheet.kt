package com.weappsinc.screenpet.feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.weappsinc.screenpet.feature.home.domain.model.HomeSettings
import com.weappsinc.screenpet.ui.theme.HomeTokens

private val MixOptionUnselectedBorder = Color(0xFFE1E4EC)
private val MixOptionUnselectedText = Color(0xFF1B2740)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMixRandomBottomSheet(
    selectedCount: Int,
    onDismiss: () -> Unit,
    onSelectCount: (Int) -> Unit,
) {
    val safe = selectedCount.coerceIn(1, HomeSettings.SLOT_COUNT)
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        dragHandle = null,
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .navigationBarsPadding()
                .padding(bottom = 24.dp),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.home_mix_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MixOptionUnselectedText,
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.home_mix_sheet_close_cd),
                        tint = Color(0xFF9AA3B2),
                        modifier = Modifier.size(22.dp),
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            val labels = listOf(
                R.string.home_mix_sheet_option_1,
                R.string.home_mix_sheet_option_2,
                R.string.home_mix_sheet_option_3,
                R.string.home_mix_sheet_option_4,
                R.string.home_mix_sheet_option_5,
                R.string.home_mix_sheet_option_6,
            )
            for (n in 1..HomeSettings.SLOT_COUNT) {
                val selected = n == safe
                MixRandomOptionRow(
                    text = stringResource(labels[n - 1]),
                    selected = selected,
                    onClick = { onSelectCount(n) },
                )
            }
        }
    }
}

@Composable
private fun MixRandomOptionRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val borderColor = if (selected) HomeTokens.SwitchCheckedTrack else MixOptionUnselectedBorder
    val bg = if (selected) HomeTokens.Pink.copy(alpha = 0.45f) else Color.White
    val textColor = if (selected) HomeTokens.SwitchCheckedTrack else MixOptionUnselectedText
    val weight = if (selected) FontWeight.SemiBold else FontWeight.Medium
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clip(RoundedCornerShape(14.dp))
            .border(1.5.dp, borderColor, RoundedCornerShape(14.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = weight,
            color = textColor,
        )
    }
}

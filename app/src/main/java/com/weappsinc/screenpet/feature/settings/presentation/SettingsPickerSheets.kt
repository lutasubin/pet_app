package com.weappsinc.screenpet.feature.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.feature.settings.domain.model.AppSettings
import com.weappsinc.screenpet.ui.theme.HomeTokens

private val durationOptions = listOf(1, 3, 5, 10, 15)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDurationBottomSheet(
    selectedMinutes: Int,
    onDismiss: () -> Unit,
    onSelectMinutes: (Int) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null,
    ) {
        Column(Modifier.navigationBarsPadding().padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.settings_duration_sheet_title),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Filled.Close, contentDescription = stringResource(R.string.settings_sheet_close_cd))
                }
            }
            Spacer(Modifier.height(8.dp))
            durationOptions.forEach { m ->
                val label = when (m) {
                    1 -> stringResource(R.string.settings_duration_option_1)
                    3 -> stringResource(R.string.settings_duration_option_3)
                    5 -> stringResource(R.string.settings_duration_option_5)
                    10 -> stringResource(R.string.settings_duration_option_10)
                    15 -> stringResource(R.string.settings_duration_option_15)
                    else -> stringResource(R.string.settings_duration_minutes, m)
                }
                Text(
                    text = label,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSelectMinutes(m)
                            onDismiss()
                        }
                        .padding(vertical = 14.dp),
                    color = if (m == selectedMinutes) HomeTokens.NavActive else HomeTokens.SwarmTitle,
                    fontWeight = if (m == selectedMinutes) FontWeight.Bold else FontWeight.Medium,
                )
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsLanguageBottomSheet(
    selectedTag: String,
    onDismiss: () -> Unit,
    onSelectTag: (String) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val options = appLanguageOptions()
    var draftSelectedTag by remember(selectedTag) { mutableStateOf(selectedTag) }
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.settings_sheet_close_cd),
                        )
                    }
                    Text(
                        text = stringResource(R.string.settings_language_sheet_title),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                    )
                    IconButton(onClick = { onSelectTag(draftSelectedTag) }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = stringResource(R.string.settings_sheet_close_cd),
                            tint = HomeTokens.SwitchCheckedTrack,
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(options, key = { it.tag }) { option ->
                        Box(Modifier.padding(vertical = 6.dp)) {
                            LanguageOptionRow(
                                option = option,
                                selected = option.tag == draftSelectedTag,
                                onClick = { draftSelectedTag = option.tag },
                            )
                        }
                    }
                }
            }
            // ModalBottomSheet ngu trong window rieng -> overlay o root activity khong che duoc.
            // Render them o day de loading hien NGAY trong sheet, tranh nhay luc sheet bat dau dong.
            LocaleTransitionOverlay()
        }
    }
}


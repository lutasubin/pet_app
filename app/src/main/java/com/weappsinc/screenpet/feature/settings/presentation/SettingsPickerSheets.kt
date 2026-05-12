package com.weappsinc.screenpet.feature.settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
    val options = languageOptions()
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null,
    ) {
        Column(Modifier.navigationBarsPadding().padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.settings_language_sheet_title),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Filled.Close, contentDescription = stringResource(R.string.settings_sheet_close_cd))
                }
            }
            Spacer(Modifier.height(8.dp))
            options.forEach { option ->
                LanguageOptionRow(
                    option = option,
                    selected = option.tag == selectedTag,
                    onClick = {
                        onSelectTag(option.tag)
                        onDismiss()
                    },
                )
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

private data class LanguageOption(
    val tag: String,
    val flagAsset: String,
    val label: String,
)

private fun languageOptions(): List<LanguageOption> = listOf(
    LanguageOption(AppSettings.LOCALE_SYSTEM, "flags/default.png", "Hệ thống"),
    LanguageOption("en-GB", "flags/UK.png", "English (UK)"),
    LanguageOption("en-US", "flags/us.png", "English (US)"),
    LanguageOption("es-ES", "flags/es.png", "Español"),
    LanguageOption("ja-JP", "flags/jp.png", "日本語"),
    LanguageOption("ko-KR", "flags/kr.png", "한국어"),
    LanguageOption("ar", "flags/sa.png", "العربية"),
    LanguageOption("vi", "flags/vn.png", "Tiếng Việt"),
)

@Composable
private fun LanguageOptionRow(
    option: LanguageOption,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick),
        color = Color.White,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = if (selected) 1.dp else 1.dp,
            color = if (selected) HomeTokens.SwitchCheckedTrack else Color(0xFFE8E8ED),
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = "file:///android_asset/${option.flagAsset}",
                contentDescription = option.label,
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape),
            )
            Text(
                text = option.label,
                style = MaterialTheme.typography.titleSmall,
                color = HomeTokens.SwarmTitle,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
            )
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = HomeTokens.SwitchCheckedTrack,
                        modifier = Modifier.size(20.dp),
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFBCC2CD),
                            shape = CircleShape,
                        ),
                ) {
                }
            }
        }
    }
}

package com.weappsinc.screenpet.feature.home.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.weappsinc.screenpet.ui.theme.HomeTokens

/** Tieu de Home theo rule text den. */
@Composable
fun HomeTitleText(
    text: String,
    modifier: Modifier = Modifier,
    useGradient: Boolean = false,
) {
    val titleStyle = MaterialTheme.typography.titleLarge
    Text(
        text = text,
        modifier = modifier,
        style = if (useGradient) {
            titleStyle.copy(
                brush = Brush.linearGradient(HomeTokens.ActivateGradient),
                fontWeight = FontWeight.ExtraBold,
            )
        } else {
            titleStyle.copy(
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
            )
        },
    )
}

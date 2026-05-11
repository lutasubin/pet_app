package com.weappsinc.screenpet.feature.home.presentation.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.ui.theme.HomeTokens

@Composable
fun ShopPetActionButton(
    unlocked: Boolean,
    downloaded: Boolean,
    onUnlock: () -> Unit,
    onDownload: () -> Unit,
) {
    when {
        !unlocked -> GradientActionButton(
            text = stringResource(R.string.home_unlock),
            onClick = onUnlock,
        )
        !downloaded -> DownloadActionButton(onClick = onDownload)
        else -> SelectedActionButton()
    }
}

@Composable
private fun GradientActionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(ACTION_HEIGHT),
        shape = RoundedCornerShape(999.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.horizontalGradient(HomeTokens.ActivateGradient)),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = text, color = Color.White, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun DownloadActionButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(ACTION_HEIGHT),
        shape = RoundedCornerShape(999.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF18BD69)),
    ) {
        Row {
            Icon(
                imageVector = Icons.Filled.CloudDownload,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.height(18.dp),
            )
            Text(
                text = " " + stringResource(R.string.shop_download),
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun SelectedActionButton() {
    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(ACTION_HEIGHT)
            .border(1.5.dp, HomeTokens.NavActive, RoundedCornerShape(999.dp)),
        shape = RoundedCornerShape(999.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = HomeTokens.NavActive,
        ),
    ) {
        Text(
            text = stringResource(R.string.shop_selected),
            color = HomeTokens.NavActive,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

private val ACTION_HEIGHT = 38.dp

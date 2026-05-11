package com.weappsinc.screenpet.feature.splash.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.ui.theme.SplashTokens

@Composable
fun SplashScreen(
    background: ImageBitmap?,
    icon: ImageBitmap?,
    progress: Float,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (background != null) {
            Image(
                painter = BitmapPainter(background),
                contentDescription = stringResource(R.string.splash_background_content_desc),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        } else {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(SplashTokens.TitleGradientTop, SplashTokens.TitleGradientBottom),
                        ),
                    ),
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 28.dp),
        ) {
            if (icon != null) {
                Image(
                    painter = BitmapPainter(icon),
                    contentDescription = stringResource(R.string.splash_icon_content_desc),
                    modifier = Modifier
                        .size(132.dp)
                        .clip(RoundedCornerShape(28.dp)),
                    contentScale = ContentScale.Fit,
                )
            } else {
                Spacer(Modifier.size(132.dp))
            }
            Spacer(Modifier.height(20.dp))
            SplashTitleText(text = stringResource(R.string.splash_title))
        }
        SplashProgressIndicator(
            progress = progress,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 28.dp, end = 28.dp, bottom = 52.dp)
                .fillMaxWidth()
                .widthIn(max = 400.dp),
        )
    }
}

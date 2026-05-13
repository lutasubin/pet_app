package com.weappsinc.screenpet.feature.onboarding.presentation.page

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.core.constants.OnboardingAssetPaths
import com.weappsinc.screenpet.feature.home.presentation.AssetImage
import com.weappsinc.screenpet.feature.onboarding.presentation.OnboardingCaption
import com.weappsinc.screenpet.feature.onboarding.presentation.idleBob

@Composable
fun OnboardingPage2(@StringRes captionRes: Int) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            AssetImage(
                assetRelativePath = OnboardingAssetPaths.PHONE_SLIDE_2,
                contentDescription = stringResource(R.string.onboarding_image_cd),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(horizontal = 36.dp)
                    .width(290.dp)
                    .height(470.dp),
            )

            PetLayer(
                assetPath = OnboardingAssetPaths.PET_SLIDE2_TOP,
                biasH = 0.4f, biasV = -0.9f,
                size = 168.dp,
                contentDescription = null,
                phaseOffsetMs = 0,
            )
            PetLayer(
                assetPath = OnboardingAssetPaths.PET_SLIDE2_BOTTOM_LEFT,
                biasH = -0.95f, biasV = 0.85f,
                size = 150.dp,
                contentDescription = null,
                phaseOffsetMs = 500,
            )

            AssetImage(
                assetRelativePath = OnboardingAssetPaths.SETTING_CARD,
                contentDescription = stringResource(R.string.onboarding_image_cd),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(260.dp)
                    .height(220.dp)
                    .idleBob(phaseOffsetMs = 200, periodMs = 2200),
            )

            AssetImage(
                assetRelativePath = OnboardingAssetPaths.HAND_POINTER,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .align(androidx.compose.ui.BiasAlignment(0.4f, 0.35f))
                    .width(110.dp)
                    .height(140.dp)
                    .idleBob(phaseOffsetMs = 700, periodMs = 1400),
            )
        }
        OnboardingCaption(text = stringResource(captionRes))
    }
}

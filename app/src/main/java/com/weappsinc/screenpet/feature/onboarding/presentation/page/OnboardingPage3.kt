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

@Composable
fun OnboardingPage3(@StringRes captionRes: Int) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            AssetImage(
                assetRelativePath = OnboardingAssetPaths.PHONE_SLIDE_3,
                contentDescription = stringResource(R.string.onboarding_image_cd),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(horizontal = 36.dp)
                    .width(290.dp)
                    .height(470.dp),
            )

            PetLayer(
                assetPath = OnboardingAssetPaths.PET_SLIDE3_TOP_LEFT,
                biasH = -0.2f, biasV = -0.95f,
                size = 140.dp,
                contentDescription = null,
                phaseOffsetMs = 0,
            )
            PetLayer(
                assetPath = OnboardingAssetPaths.PET_SLIDE3_MID_LEFT,
                biasH = -0.95f, biasV = 0f,
                size = 130.dp,
                contentDescription = null,
                phaseOffsetMs = 300,
            )
            PetLayer(
                assetPath = OnboardingAssetPaths.PET_SLIDE3_MID_RIGHT,
                biasH = 0.95f, biasV = -0.1f,
                size = 130.dp,
                contentDescription = null,
                phaseOffsetMs = 500,
            )
            PetLayer(
                assetPath = OnboardingAssetPaths.PET_SLIDE3_BOTTOM_RIGHT,
                biasH = 0.9f, biasV = 0.85f,
                size = 150.dp,
                contentDescription = null,
                phaseOffsetMs = 800,
            )
        }
        OnboardingCaption(text = stringResource(captionRes))
    }
}

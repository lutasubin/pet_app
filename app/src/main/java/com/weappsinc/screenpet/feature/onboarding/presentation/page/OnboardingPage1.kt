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
import com.weappsinc.screenpet.feature.home.presentation.SvgAssetIcon
import com.weappsinc.screenpet.feature.onboarding.presentation.OnboardingCaption

@Composable
fun OnboardingPage1(@StringRes captionRes: Int) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            SvgAssetIcon(
                assetRelativePath = OnboardingAssetPaths.PHONE_SLIDE_1_SVG,
                contentDescription = stringResource(R.string.onboarding_image_cd),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(horizontal = 36.dp)
                    .width(280.dp)
                    .height(460.dp),
            )

            PetLayer(
                assetPath = OnboardingAssetPaths.PET_SLIDE1_TOP_LEFT,
                biasH = -0.72f, biasV = -0.62f,
                size = 130.dp,
                contentDescription = null,
                phaseOffsetMs = 0,
            )
            PetLayer(
                assetPath = OnboardingAssetPaths.PET_SLIDE1_MID_LEFT,
                biasH = -0.74f, biasV = 0.08f,
                size = 130.dp,
                contentDescription = null,
                phaseOffsetMs = 400,
            )
            PetLayer(
                assetPath = OnboardingAssetPaths.PET_SLIDE1_MID_RIGHT,
                biasH = 0.74f, biasV = -0.12f,
                size = 130.dp,
                contentDescription = null,
                phaseOffsetMs = 200,
            )
            PetLayer(
                assetPath = OnboardingAssetPaths.PET_SLIDE1_BOTTOM_LEFT,
                biasH = -0.42f, biasV = 0.84f,
                size = 90.dp,
                contentDescription = null,
                phaseOffsetMs = 600,
            )
            PetLayer(
                assetPath = OnboardingAssetPaths.PET_SLIDE1_BOTTOM_RIGHT,
                biasH = 0.68f, biasV = 0.84f,
                size = 150.dp,
                contentDescription = null,
                phaseOffsetMs = 800,
            )
        }
        OnboardingCaption(text = stringResource(captionRes))
    }
}

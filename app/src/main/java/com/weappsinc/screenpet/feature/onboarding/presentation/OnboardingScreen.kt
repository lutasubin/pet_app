package com.weappsinc.screenpet.feature.onboarding.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.core.constants.OnboardingAssetPaths
import com.weappsinc.screenpet.feature.home.presentation.AssetImage
import com.weappsinc.screenpet.feature.onboarding.presentation.page.OnboardingPage1
import com.weappsinc.screenpet.feature.onboarding.presentation.page.OnboardingPage2
import com.weappsinc.screenpet.feature.onboarding.presentation.page.OnboardingPage3
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { OnboardingTokens.PageCount })
    val scope = rememberCoroutineScope()
    Box(modifier = modifier.fillMaxSize()) {
        AssetImage(
            assetRelativePath = OnboardingAssetPaths.BACKGROUND,
            contentDescription = stringResource(R.string.onboarding_image_cd),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            verticalArrangement = Arrangement.Top,
        ) {
            OnboardingTopBar(
                currentIndex = pagerState.currentPage,
                pageCount = OnboardingTokens.PageCount,
                onNext = { advanceOrFinish(pagerState, scope, onFinish) },
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f, fill = true),
            ) { page ->
                when (page) {
                    0 -> OnboardingPage1(captionRes = R.string.onboarding_caption_1)
                    1 -> OnboardingPage2(captionRes = R.string.onboarding_caption_2)
                    else -> OnboardingPage3(captionRes = R.string.onboarding_caption_3)
                }
            }
        }
    }
}

private fun advanceOrFinish(
    state: PagerState,
    scope: kotlinx.coroutines.CoroutineScope,
    onFinish: () -> Unit,
) {
    val current = state.currentPage
    if (current >= OnboardingTokens.PageCount - 1) {
        onFinish()
    } else {
        scope.launch { state.animateScrollToPage(current + 1) }
    }
}

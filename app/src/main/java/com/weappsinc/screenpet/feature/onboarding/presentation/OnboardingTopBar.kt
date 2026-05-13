package com.weappsinc.screenpet.feature.onboarding.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R

@Composable
fun OnboardingTopBar(
    currentIndex: Int,
    pageCount: Int,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isLast = currentIndex == pageCount - 1
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(OnboardingTokens.TopBarHeight)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        OnboardingProgressIndicator(
            currentIndex = currentIndex,
            pageCount = pageCount,
            modifier = Modifier.semantics {
                contentDescription = "${currentIndex + 1} / $pageCount"
            },
        )
        TextButton(onClick = onNext) {
            val labelRes = if (isLast) R.string.onboarding_action_start else R.string.onboarding_action_next
            Text(
                text = stringResource(labelRes),
                color = OnboardingTokens.NextLabel,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun OnboardingProgressIndicator(
    currentIndex: Int,
    pageCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(OnboardingTokens.ProgressBarGap),
    ) {
        repeat(pageCount) { idx ->
            val active = idx == currentIndex
            Box(
                modifier = Modifier
                    .height(OnboardingTokens.ProgressBarHeight)
                    .width(OnboardingTokens.ProgressBarSegmentWidth)
                    .background(
                        color = if (active) OnboardingTokens.ProgressActive else OnboardingTokens.ProgressInactive,
                        shape = CircleShape,
                    ),
            )
        }
        Box(modifier = Modifier.size(0.dp))
    }
}

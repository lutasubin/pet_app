package com.weappsinc.screenpet.feature.settings.presentation

import androidx.annotation.StringRes
import com.weappsinc.screenpet.R

data class RateSheetCopy(
    @StringRes val titleRes: Int?,
    @StringRes val bodyRes: Int,
)

fun rateSheetCopyFor(stars: Int): RateSheetCopy = when (stars.coerceIn(0, 5)) {
    0 -> RateSheetCopy(titleRes = null, bodyRes = R.string.settings_rate_body_initial)
    in 1..3 -> RateSheetCopy(
        titleRes = R.string.settings_rate_title_negative,
        bodyRes = R.string.settings_rate_body_negative,
    )
    4 -> RateSheetCopy(
        titleRes = R.string.settings_rate_title_four,
        bodyRes = R.string.settings_rate_body_positive,
    )
    else -> RateSheetCopy(
        titleRes = R.string.settings_rate_title_five,
        bodyRes = R.string.settings_rate_body_positive,
    )
}

package com.weappsinc.screenpet.feature.onboarding.presentation

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weappsinc.screenpet.feature.onboarding.domain.usecase.MarkLanguagePickerCompletedUseCase
import com.weappsinc.screenpet.feature.settings.domain.usecase.SetLocaleTagUseCase
import com.weappsinc.screenpet.feature.settings.presentation.AppLocaleApplier
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class FirstRunLanguageViewModel @Inject constructor(
    private val setLocaleTag: SetLocaleTagUseCase,
    private val markLanguagePickerCompleted: MarkLanguagePickerCompletedUseCase,
) : ViewModel() {

    fun confirmLocale(activity: ComponentActivity, tag: String, onDone: () -> Unit) {
        viewModelScope.launch {
            setLocaleTag(tag)
            markLanguagePickerCompleted()
            AppLocaleApplier.applyAndRecreate(activity, tag)
            onDone()
        }
    }
}

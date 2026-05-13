package com.weappsinc.screenpet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.weappsinc.screenpet.feature.pet.presentation.PetViewModel
import com.weappsinc.screenpet.feature.splash.presentation.SplashViewModel
import com.weappsinc.screenpet.ui.theme.App_petTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val petViewModel: PetViewModel by viewModels()
    private val splashViewModel: SplashViewModel by viewModels()
    private val appEntryViewModel: AppEntryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App_petTheme {
                AppEntryContent(
                    activity = this@MainActivity,
                    splashViewModel = splashViewModel,
                    petViewModel = petViewModel,
                    appEntryViewModel = appEntryViewModel,
                )
            }
        }
    }
}

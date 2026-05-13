package com.weappsinc.screenpet.feature.onboarding.di

import com.weappsinc.screenpet.feature.onboarding.data.DataStoreOnboardingRepository
import com.weappsinc.screenpet.feature.onboarding.domain.repository.OnboardingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OnboardingBindModule {

    @Binds
    @Singleton
    abstract fun bindOnboarding(impl: DataStoreOnboardingRepository): OnboardingRepository
}

package com.weappsinc.screenpet.feature.settings.di

import com.weappsinc.screenpet.feature.settings.data.DataStoreAppSettingsRepository
import com.weappsinc.screenpet.feature.settings.domain.repository.AppSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsBindModule {

    @Binds
    @Singleton
    abstract fun bindAppSettings(impl: DataStoreAppSettingsRepository): AppSettingsRepository
}

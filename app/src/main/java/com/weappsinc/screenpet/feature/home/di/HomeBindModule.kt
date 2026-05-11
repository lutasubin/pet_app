package com.weappsinc.screenpet.feature.home.di

import com.weappsinc.screenpet.feature.home.data.AndroidAssetDirectoryLister
import com.weappsinc.screenpet.feature.home.data.AssetDirectoryLister
import com.weappsinc.screenpet.feature.home.data.AssetShimejiCatalogRepository
import com.weappsinc.screenpet.feature.home.data.DataStoreHomeSettingsRepository
import com.weappsinc.screenpet.feature.home.data.DataStoreShimejiUnlockRepository
import com.weappsinc.screenpet.feature.home.domain.repository.HomeSettingsRepository
import com.weappsinc.screenpet.feature.home.domain.repository.ShimejiCatalogRepository
import com.weappsinc.screenpet.feature.home.domain.repository.ShimejiUnlockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeBindModule {

    @Binds
    @Singleton
    abstract fun bindCatalog(impl: AssetShimejiCatalogRepository): ShimejiCatalogRepository

    @Binds
    @Singleton
    abstract fun bindUnlock(impl: DataStoreShimejiUnlockRepository): ShimejiUnlockRepository

    @Binds
    @Singleton
    abstract fun bindSettings(impl: DataStoreHomeSettingsRepository): HomeSettingsRepository

    @Binds
    @Singleton
    abstract fun bindAssetLister(impl: AndroidAssetDirectoryLister): AssetDirectoryLister
}

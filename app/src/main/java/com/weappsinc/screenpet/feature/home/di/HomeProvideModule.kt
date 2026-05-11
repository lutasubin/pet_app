package com.weappsinc.screenpet.feature.home.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.weappsinc.screenpet.feature.home.data.HomePreferencesKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.homeDataStore: DataStore<Preferences> by preferencesDataStore(
    name = HomePreferencesKeys.DATASTORE_NAME,
)

@Module
@InstallIn(SingletonComponent::class)
object HomeProvideModule {

    @Provides
    @Singleton
    fun provideHomeDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.homeDataStore
}

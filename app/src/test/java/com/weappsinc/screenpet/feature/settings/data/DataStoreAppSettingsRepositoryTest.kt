package com.weappsinc.screenpet.feature.settings.data

import com.weappsinc.screenpet.feature.settings.domain.model.AppSettings
import java.io.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences

class DataStoreAppSettingsRepositoryTest {

    private lateinit var file: File
    private lateinit var scope: CoroutineScope
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var repository: DataStoreAppSettingsRepository

    @Before
    fun setup() {
        file = File.createTempFile("settings_test_", ".preferences_pb")
        scope = CoroutineScope(Dispatchers.Unconfined + Job())
        dataStore = PreferenceDataStoreFactory.create(scope = scope, produceFile = { file })
        repository = DataStoreAppSettingsRepository(dataStore)
    }

    @After
    fun tearDown() {
        scope.cancel()
        file.delete()
    }

    @Test
    fun ghost_va_size_speed_duration_locale_doc_ghi_dung() = runBlocking {
        assertEquals(false, repository.settings.first().ghostModeEnabled)
        repository.setGhostMode(true)
        assertTrue(repository.settings.first().ghostModeEnabled)

        repository.setAnimationSizeScale(1.25f)
        assertEquals(1.25f, repository.settings.first().animationSizeScale, 0.001f)

        repository.setAnimationSpeedMultiplier(1.2f)
        assertEquals(1.2f, repository.settings.first().animationSpeedMultiplier, 0.001f)

        repository.setSessionDurationMinutes(10)
        assertEquals(10, repository.settings.first().sessionDurationMinutes)

        repository.setLocaleTag("vi")
        assertEquals("vi", repository.settings.first().localeTag)
    }

    @Test
    fun scale_speed_bi_clamp() = runBlocking {
        repository.setAnimationSizeScale(10f)
        assertEquals(AppSettings.MAX_SIZE_SCALE, repository.settings.first().animationSizeScale, 0.001f)
        repository.setAnimationSpeedMultiplier(0.01f)
        assertEquals(AppSettings.MIN_SPEED_MULT, repository.settings.first().animationSpeedMultiplier, 0.001f)
    }
}

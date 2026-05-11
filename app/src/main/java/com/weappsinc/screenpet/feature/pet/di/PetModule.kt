package com.weappsinc.screenpet.feature.pet.di

import com.weappsinc.screenpet.feature.pet.data.PetSimulationRepositoryImpl
import com.weappsinc.screenpet.feature.pet.domain.repository.PetSimulationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PetModule {

    @Binds
    @Singleton
    abstract fun bindPetSimulationRepository(impl: PetSimulationRepositoryImpl): PetSimulationRepository
}

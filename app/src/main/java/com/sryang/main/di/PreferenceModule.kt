package com.sryang.main.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule() {
//    @Binds
//    abstract fun provideLocationPreferences(locationPreferences: LocationPreferencesImpl): LocationPreferences
//    abstract fun provideLocationPreferences(locationPreferences: TestLocationPreferencesImpl): LocationPreferences
}
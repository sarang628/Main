package com.sryang.torang_repository.di.repository

import com.sryang.torang_repository.repository.profile.ProfileRepository
import com.sryang.torang_repository.repository.profile.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ProfileRepositoryModule {
    @Binds
    abstract fun provideLoginRepository(profileRepository: ProfileRepositoryImpl): ProfileRepository
}
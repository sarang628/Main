package com.sryang.torang_repository.di.repository

import com.sryang.torang_repository.repository.login.LoginRepository
import com.sryang.torang_repository.repository.login.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class LoginRepositoryModule {
    @Binds
    abstract fun provideLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository
}
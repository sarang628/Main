package com.sryang.splash.di.splash

import com.sarang.screen_splash.SplashService
import com.sryang.torang_repository.api.ApiLogin
import com.sryang.torang_repository.session.SessionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.delay

@InstallIn(SingletonComponent::class)
@Module
class SplashModule {
    @Provides
    fun provideSplashService(
        sessionService: SessionService,
        apiLogin: ApiLogin
    ): SplashService {
        return object : SplashService {
            override suspend fun checkSession(): Boolean {
                sessionService.getToken()?.let {
                    return apiLogin.sessionCheck(it)
                }
                return false
            }
        }
    }
}
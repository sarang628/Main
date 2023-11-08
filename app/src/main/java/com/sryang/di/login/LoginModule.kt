package com.sryang.login.di.login

import android.content.Context
import com.sarang.toringlogin.login.usecase.EmailLoginUseCase
import com.sarang.toringlogin.login.usecase.SignUpUseCase
import com.sryang.torang_repository.repository.LoginRepository
import com.sryang.torang_repository.session.SessionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginServiceModule {
    @Singleton
    @Provides
    fun emailLoginService(
        loginRepository: LoginRepository,
        sessionService: SessionService
    ): EmailLoginUseCase {
        return object : EmailLoginUseCase {
            override suspend fun emailLogin(id: String, email: String) {
                val result = loginRepository.emailLogin(id, email).token
                sessionService.saveToken(result)
            }

            override suspend fun saveToken(token: String) {
                return sessionService.saveToken(token = token)
            }

            override suspend fun logout() {
                sessionService.removeToken()
            }

            override val isLogin: StateFlow<Boolean>
                get() = loginRepository.isLogin
        }
    }

    @Singleton
    @Provides
    fun sessionService(@ApplicationContext context: Context): SessionService {
        return SessionService(context)
    }

    @Singleton
    @Provides
    fun provideSignUpUseCase(
        loginRepository: LoginRepository
    ): SignUpUseCase {
        return object : SignUpUseCase {
            override suspend fun confirmCode(
                token: String,
                confirmCode: String,
                name: String,
                email: String,
                password: String
            ): Boolean {
                return loginRepository.confirmCode(
                    token = token,
                    confirmCode = confirmCode,
                    name = name,
                    email = email,
                    password = password
                )
            }

            override suspend fun checkEmail(email: String, password: String): String {
                return loginRepository.checkEmail(email, password)
            }
        }
    }
}
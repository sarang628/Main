package com.sryang.myapplication.di.profile

import com.sarang.profile.uistate.ProfileUiState
import com.sarang.profile.viewmodel.ProfileService
import com.sryang.torang_repository.repository.profile.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ProfileRepositoryAdapterModule {
    @Provides
    fun provideProfileAdapterRepository(
        profileRepository: ProfileRepositoryImpl
    ): ProfileService {
        return object : ProfileService {
            override suspend fun loadProfile(i: Int): ProfileUiState {
                val result = profileRepository.loadProfile(i)
                return ProfileUiState(
                    profileUrl = result.profilePicUrl,
                    feedCount = result.reviewCount,
                    following = result.following,
                    follower = result.followers,
                    name = result.userName,
                    isLogin = true
                )
            }
        }
    }
}
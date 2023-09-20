package com.sryang.myapplication.di

import com.sryang.library.entity.User
import com.sryang.torang_repository.data.entity.UserEntity
import com.sryang.torang_repository.repository.profile.ProfileRepository
import com.sryang.torang_repository.repository.profile.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ProfileRepositoryAdapterModule {
    @Provides
    fun provideProfileAdapterRepository(
        profileRepository: ProfileRepositoryImpl
    ): com.sarang.profile.viewmodel.ProfileRepository {
        return object : com.sarang.profile.viewmodel.ProfileRepository {
            override suspend fun loadProfile(i: Int): User {
                return profileRepository.loadProfile(i).toUser()
            }
        }
    }

    fun UserEntity.toUser(): User {
        return User(
            userId = this.userId,
            name = this.userName ?: "",
            profilePictureUrl = this.profile_pic_url ?: ""
        )
    }
}
package com.sryang.main.di

import com.example.torang_core.data.model.ReviewImage
import com.example.torang_core.datasource.local.MyReviewsLocalDataSource
import com.example.torang_core.datasource.local.MyReviewsRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourcesModule {
    @Binds
    abstract fun provideLocalDataSource(myReviewsLocalDataSourceImpl: MyReviewsLocalDataSourceImpl): MyReviewsLocalDataSource

    @Binds
    abstract fun provideRemoteDataSource(myReviewsRemoteDataSourceImpl: MyReviewsRemoteDataSourceImpl): MyReviewsRemoteDataSource
}

class MyReviewsLocalDataSourceImpl @Inject constructor() : MyReviewsLocalDataSource{
    override suspend fun getMyReviews(): List<ReviewImage> {
        return ArrayList()
    }
}

class MyReviewsRemoteDataSourceImpl @Inject constructor() : MyReviewsRemoteDataSource{
    override suspend fun getMyReviews(): List<ReviewImage> {
        return ArrayList()
    }
}
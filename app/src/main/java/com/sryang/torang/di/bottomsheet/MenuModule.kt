package com.sryang.torang.di.bottomsheet

import android.util.Log
import com.sryang.torang.usecase.IsMyReviewUseCase
import com.sryang.torang_repository.data.dao.LoggedInUserDao
import com.sryang.torang_repository.data.dao.ReviewDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MenuModule {
    @Provides
    fun ProvidesGetReviewUseCase(
        reviewDao: ReviewDao,
        loggedInUserDao: LoggedInUserDao
    ): IsMyReviewUseCase {
        return object : IsMyReviewUseCase {
            override suspend fun invoke(reviewId: Int): Boolean {
                try {
                    val review = reviewDao.getFeedbyReviewId(reviewId = reviewId)
                    Log.d("_MenuModule", review.toString())
                    val userId = loggedInUserDao.getLoggedInUser1()?.userId
                    Log.d("_MenuModule", "userId : ${userId}")
                    return review.userId == userId
                } catch (e: Exception) {
                    Log.e("_MenuModule", e.message.toString())
                }
                return false
            }
        }
    }
}
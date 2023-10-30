package com.sryang.addreview.di.addreview

import com.sryang.addreview.usecase.ReviewUseCase
import com.sryang.torang_repository.api.ApiReview
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MultipartBody
import okhttp3.RequestBody

@InstallIn(SingletonComponent::class)
@Module
class ReviewServiceImpl {
    @Provides
    fun provideReviewService(apiReview: ApiReview): ReviewUseCase {
        return object : ReviewUseCase {
            override suspend fun addReview(
                params: HashMap<String, RequestBody>,
                file: ArrayList<MultipartBody.Part>
            ): String {
                apiReview.addReview(params, file)
                return ""
            }

        }
    }

}
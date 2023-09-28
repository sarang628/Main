package com.sryang.addreview.di.addreview

import com.sryang.library.ReviewService
import com.sryang.torang_repository.api.ApiReview
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@InstallIn(SingletonComponent::class)
@Module
class ReviewServiceImpl {

    @Inject
    lateinit var remoteReviewService: ApiReview
    @Provides
    fun provideReviewService(): ReviewService {
        return object : ReviewService {
            override suspend fun addReview(
                params: HashMap<String, RequestBody>,
                file: ArrayList<MultipartBody.Part>
            ): String {
                remoteReviewService.addReview(params, file)
                return ""
            }

        }
    }

}
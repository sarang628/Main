package com.sryang.torang.report.test.di.report_di

import com.sryang.torang.data.dto.ReviewDTO
import com.sryang.torang.usecases.report.BlockUserUseCase
import com.sryang.torang.usecases.report.LoadReviewUseCase
import com.sryang.torang.usecases.report.ReportUseCase
import com.sryang.torang_repository.api.ApiReview
import com.sryang.torang_repository.api.handle
import com.sryang.torang_repository.repository.ReportReason
import com.sryang.torang_repository.repository.ReportRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.HttpException

@InstallIn(SingletonComponent::class)
@Module
class ReportUseCaseModule
{
    @Provides
    fun providesBlockUserUseCase(): BlockUserUseCase
    {
        return object : BlockUserUseCase
        {
            override suspend fun invoke(userId: Int)
            {

            }
        }
    }

    @Provides
    fun providesReportUseCase(reportRepository: ReportRepository): ReportUseCase
    {
        return object : ReportUseCase
        {
            override suspend fun invoke(reviewId: Int, reason: String)
            {
                reportRepository.sendReportReason(
                    reviewId = reviewId, reportReason = ReportReason.ABUSE
                )
            }
        }
    }

    @Provides
    fun providesLoadReviewUseCase(
        apiReview: ApiReview
    ): LoadReviewUseCase
    {
        return object : LoadReviewUseCase
        {
            override suspend fun invoke(id: Int): ReviewDTO
            {
                try
                {
                    val result = apiReview.getReviewsById(id)
                    return ReviewDTO(
                        userId = result.user.userId,
                        reviewId = result.reviewId,
                        profileUrl = result.user.profilePicUrl,
                        userName = result.user.userName
                    )
                } catch (e: HttpException)
                {
                    throw Exception(e.handle())
                }
            }
        }
    }

}
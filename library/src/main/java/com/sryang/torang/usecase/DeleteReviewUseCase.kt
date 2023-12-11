package com.sryang.torang.usecase

interface DeleteReviewUseCase {
    suspend fun invoke(reviewId : Int)
}
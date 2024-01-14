package com.sarang.torang.usecase

interface DeleteReviewUseCase {
    suspend fun invoke(reviewId : Int)
}
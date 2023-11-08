package com.sryang.di.feed

import com.sryang.torang.data.CommentDataUiState
import com.sryang.torang.data.FeedData
import com.sryang.torang.usecase.FeedService
import com.sryang.torang_repository.repository.FeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * DataLayer 과 UILayer을 연결해주는 역할
 * ViewModel안에서 Repository를 바로 주입할 수 있지만
 * UI를 DayaLayer와 완전히 분리시켜보기위해 구현
 * DomainLayer의 역할
 */
@InstallIn(SingletonComponent::class)
@Module
class FeedServiceModule {
    @Provides
    fun provideFeedService(
        feedRepository: FeedRepository
    ): FeedService {
        return object : FeedService {
            override suspend fun getFeeds() {
                feedRepository.loadFeed()
            }

            override val feeds: Flow<List<FeedData>>
                get() = feedRepository.feeds.map { it ->
                    it.stream().map {
                        it.toFeedData()
                    }.toList()
                }

            override suspend fun addLike(reviewId: Int) {
                feedRepository.addLike(reviewId)
            }

            override suspend fun deleteLike(reviewId: Int) {
                feedRepository.deleteLike(reviewId)
            }

            override suspend fun deleteFavorite(reviewId: Int) {
                feedRepository.deleteFavorite(reviewId)
            }

            override suspend fun addFavorite(reviewId: Int) {
                feedRepository.addFavorite(reviewId)
            }

            override suspend fun getComment(reviewId: Int): CommentDataUiState {
                val result = feedRepository.getComment(reviewId)
                return CommentDataUiState(
                    myProfileUrl = result.profilePicUrl,
                    commentList = result.list.stream().map { it.toCommentData() }.toList()
                )
            }

            override suspend fun addComment(reviewId: Int, comment: String) {
                feedRepository.addComment(reviewId, comment)
            }
        }
    }
}
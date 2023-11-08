package com.sryang.di.feed

import com.sarang.base_feed.uistate.FeedBottomUIState
import com.sarang.base_feed.uistate.FeedTopUIState
import com.sarang.base_feed.uistate.FeedUiState
import com.sryang.library.CommentItemUiState
import com.sryang.torang.data.CommentData
import com.sryang.torang.data.FeedData
import com.sryang.torang_repository.data.RemoteComment
import com.sryang.torang_repository.data.entity.FeedEntity
import com.sryang.torang_repository.data.entity.ReviewAndImageEntity
import com.sryang.torang_repository.data.remote.response.RemoteFeed

fun RemoteComment.toCommentData(): CommentData {
    return CommentData(
        userId = this.user.userId,
        profileImageUrl = this.user.profilePicUrl,
        date = this.create_date,
        comment = this.comment,
        name = this.user.userName,
        likeCount = 0
    )
}

fun FeedEntity.toFeedTopUiState(): FeedTopUIState {
    return FeedTopUIState(
        reviewId = this.reviewId,
        name = this.userName,
        profilePictureUrl = this.profilePicUrl,
        rating = this.rating,
        restaurantName = this.restaurantName,
        userId = this.userId,
        restaurantId = this.restaurantId
    )
}

fun FeedEntity.toFeedBottomUiState(): FeedBottomUIState {
    return FeedBottomUIState(
        reviewId = this.reviewId,
        likeAmount = this.likeAmount,
        commentAmount = this.commentAmount,
        author = "",
        author1 = "",
        author2 = "",
        comment = "",
        comment1 = "",
        comment2 = "",
        isLike = false,
        isFavorite = false,
        visibleLike = true,
        visibleComment = true,
        contents = this.contents
    )
}

fun RemoteFeed.toFeedBottomUiState(): FeedBottomUIState {
    return FeedBottomUIState(
        reviewId = this.reviewId,
        likeAmount = this.like_amount,
        commentAmount = this.comment_amount,
        author = "",
        author1 = "",
        author2 = "",
        comment = "",
        comment1 = "",
        comment2 = "",
        isLike = this.like != null,
        isFavorite = this.favorite != null,
        visibleLike = true,
        visibleComment = true,
        contents = this.contents
    )
}

fun RemoteFeed.toFeedTopUiState(): FeedTopUIState {
    return FeedTopUIState(
        reviewId = this.reviewId,
        name = this.user.userName,
        profilePictureUrl = this.user.profilePicUrl,
        rating = this.rating,
        restaurantName = this.restaurant.restaurantName,
        userId = this.user.userId,
        restaurantId = this.restaurant.restaurantId
    )
}

fun FeedData.toFeedUiState(): FeedUiState {
    return FeedUiState(
        reviewId = this.reviewId,
        itemFeedBottomUiState = this.toFeedBottomUIState(),
        itemFeedTopUiState = this.toFeedTopUIState(),
        reviewImages = this.reviewImages
    )
}

fun FeedData.toFeedBottomUIState(
): FeedBottomUIState {
    return FeedBottomUIState(
        reviewId = this.reviewId,
        likeAmount = this.likeAmount,
        commentAmount = this.commentAmount,
        author = this.author,
        author1 = this.author1,
        author2 = this.author2,
        comment = this.comment,
        comment1 = this.comment1,
        comment2 = this.comment2,
        isLike = this.isLike,
        isFavorite = this.isFavorite,
        visibleLike = this.visibleLike,
        visibleComment = this.visibleComment,
        contents = this.contents
    )
}

fun FeedData.toFeedTopUIState(): FeedTopUIState {
    return FeedTopUIState(
        reviewId = this.reviewId,
        userId = this.userId,
        name = this.name,
        restaurantName = this.restaurantName,
        rating = this.rating,
        profilePictureUrl = this.profilePictureUrl,
        restaurantId = restaurantId
    )
}

fun CommentData.toCommentItemUiState(): CommentItemUiState {
    return CommentItemUiState(
        userId = userId,
        profileImageUrl = profileImageUrl,
        date = date,
        comment = comment,
        name = name,
        likeCount = likeCount
    )
}

fun ReviewAndImageEntity.toFeedData(): FeedData {
    return FeedData(
        reviewId = this.review.reviewId,
        userId = this.review.userId,
        name = this.review.userName,
        restaurantName = this.review.restaurantName,
        rating = this.review.rating,
        profilePictureUrl = this.review.profilePicUrl,
        likeAmount = this.review.likeAmount,
        commentAmount = this.review.commentAmount,
        author = "",
        author1 = "",
        author2 = "",
        comment = "",
        comment1 = "",
        comment2 = "",
        isLike = this.like != null,
        isFavorite = this.favorite != null,
        visibleLike = false,
        visibleComment = false,
        contents = this.review.contents,
        reviewImages = this.images.stream().map { it.pictureUrl }.toList(),
        restaurantId = this.review.restaurantId
    )
}
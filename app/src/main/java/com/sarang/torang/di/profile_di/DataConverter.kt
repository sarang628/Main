package com.sryang.myapplication.di.profile_di


import com.sryang.torang.compose.follow.Follow
import com.sryang.torang.uistate.Feed
import com.sryang.torang.viewmodel.FollowUiState
import com.sryang.torang_repository.data.entity.ReviewAndImageEntity
import com.sryang.torang_repository.data.remote.response.RemoteFollower
import com.sryang.torang_repository.data.remote.response.RemoteUser


fun ReviewAndImageEntity.toFeed(): Feed {
    return Feed(
        this.review.reviewId,
        this.review.userId,
        this.review.restaurantId,
        this.review.userName,
        this.review.restaurantName,
        this.review.profilePicUrl,
        this.review.contents,
        this.review.rating,
        this.review.likeAmount,
        this.review.commentAmount,
        this.review.createDate,
        reviewImage = this.images.map { it.pictureUrl },
        isLike = this.like != null,
        isFavorite = this.favorite != null
    )
}

fun List<ReviewAndImageEntity>.toFeeds(): List<Feed> {
    return this.map { it.toFeed() }
}

fun RemoteFollower.toFollow(): Follow {
    return Follow(
        url = this.profilePicUrl,
        name = this.userName,
        nickname = this.userName,
        id = this.followerId,
        isFollow = false
    )
}

fun RemoteUser.toFollowUiState(): FollowUiState {
    return FollowUiState(
        name = this.userName, following = this.following, follower = this.follower, subscription = 0
    )
}
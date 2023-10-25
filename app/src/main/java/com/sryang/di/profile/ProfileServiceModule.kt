package com.sryang.myapplication.di.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.base_feed.ui.Feeds
import com.sarang.base_feed.uistate.FeedBottomUIState
import com.sarang.base_feed.uistate.FeedTopUIState
import com.sarang.base_feed.uistate.FeedUiState
import com.sarang.profile._ProfileScreen
import com.sarang.profile.uistate.Feed
import com.sarang.profile.uistate.ProfileUiState
import com.sarang.profile.viewmodel.ProfileService
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sryang.torang_repository.data.entity.ReviewAndImageEntity
import com.sryang.torang_repository.repository.EditProfileRepository
import com.sryang.torang_repository.repository.impl.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combineTransform
import kotlin.streams.toList

@Module
@InstallIn(SingletonComponent::class)
class ProfileServiceModule {
    @Provides
    fun provideProfileService(
        profileRepository: ProfileRepositoryImpl,
        editProfileRepository: EditProfileRepository
    ): ProfileService {
        return object : ProfileService {
            override suspend fun loadProfile(id: Int): ProfileUiState {
                val result = profileRepository.loadProfile(id)
                return ProfileUiState(
                    profileUrl = result.profilePicUrl,
                    feedCount = result.reviewCount,
                    following = result.following,
                    follower = result.followers,
                    name = result.userName,
                    isLogin = true
                )
            }

            override suspend fun loadProfileByToken(): ProfileUiState {
                val result = profileRepository.loadProfileByToken()
                return ProfileUiState(
                    profileUrl = result.profilePicUrl,
                    feedCount = result.reviewCount,
                    following = result.following,
                    follower = result.followers,
                    name = result.userName,
                    isLogin = true
                )
            }

            override suspend fun getFavorites(): kotlinx.coroutines.flow.Flow<List<Feed>> {
                return MutableStateFlow<List<Feed>>(ArrayList()).combineTransform(
                    profileRepository.getMyFavorite(
                        1
                    )
                ) { feed, feedEntity ->
                    emit(feedEntity.toFeeds())
                }
            }

            override suspend fun updateProfile(uri: String) {
                editProfileRepository.editProfile(name = null, uri = uri)
            }
        }
    }
}


fun List<ReviewAndImageEntity>.toFeeds(): List<Feed> {
    return this.stream().map { it.toFeed() }.toList()
}

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
        reviewImage = this.images.stream().map { it.pictureUrl }.toList(),
        isLike = this.like != null,
        isFavorite = this.favorite != null
    )
}


@Composable
fun ProfileScreen(
    id: Int? = null,
    isMyProfile: Boolean,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    profileImageUrl: String,
    imageServerUrl: String,
    onEditProfile: () -> Unit,
    onSetting: () -> Unit
) {
    val uiState by profileViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = isMyProfile, block = {
        if (isMyProfile) {
            profileViewModel.loadProfileByToken()
        } else {
            id?.let {
                profileViewModel.loadProfile(it)
            }
        }
    })

    _ProfileScreen(
        isMyProfile = isMyProfile,
        profileBaseUrl = profileImageUrl,
        profileViewModel = profileViewModel,
        onSetting = onSetting,
        onEditProfile = onEditProfile,
        favorite = {
            Feeds(
                list = uiState.favoriteList?.toFeedUiState() ?: ArrayList(),
                onProfile = {},
                onLike = {},
                onComment = {},
                onShare = {},
                onFavorite = {},
                onMenu = { /*TODO*/ },
                onName = { /*TODO*/ },
                onRestaurant = { /*TODO*/ },
                onImage = {},
                onRefresh = { /*TODO*/ },
                isRefreshing = false,
                profileImageServerUrl = profileImageUrl,
                imageServerUrl = imageServerUrl
            )
        },
        wantToGo = {
            Feeds(
                list = ArrayList<FeedUiState>().apply {
                },
                onProfile = {},
                onLike = {},
                onComment = {},
                onShare = {},
                onFavorite = {},
                onMenu = { /*TODO*/ },
                onName = { /*TODO*/ },
                onRestaurant = { /*TODO*/ },
                onImage = {},
                onRefresh = { /*TODO*/ },
                isRefreshing = false
            )
        }
    )
}

fun List<Feed>.toFeedUiState(): ArrayList<FeedUiState> {
    return ArrayList(this.stream().map { it.toFeedUiState() }.toList())
}

fun Feed.toFeedUiState(): FeedUiState {
    return FeedUiState(
        reviewId = this.reviewId,
        itemFeedTopUiState = this.toFeedTopUiState(),
        itemFeedBottomUiState = this.toFeedBottomUiState(),
        reviewImages = this.reviewImage,
    )
}

fun Feed.toFeedTopUiState(): FeedTopUIState {
    return FeedTopUIState(
        restaurantId = this.restaurantId,
        reviewId = this.reviewId,
        userId = this.userId,
        name = this.userName,
        restaurantName = this.restaurantName,
        profilePictureUrl = this.profilePicUrl,
        rating = this.rating
    )
}

fun Feed.toFeedBottomUiState(): FeedBottomUIState {
    return FeedBottomUIState(
        reviewId = this.reviewId,
        author = "",
        author2 = "",
        author1 = "",
        comment = "",
        commentAmount = this.commentAmount,
        comment2 = "",
        comment1 = "",
        contents = this.contents,
        isFavorite = this.isFavorite,
        isLike = this.isLike,
        likeAmount = this.likeAmount,
        visibleComment = false,
        visibleLike = false
    )
}
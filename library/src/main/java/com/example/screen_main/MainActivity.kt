package com.example.screen_main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.screen_feed.FeedsViewModel
import com.sarang.profile.viewmodel.ProfileRepository
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sarang.toringlogin.login.LoginViewModel
import com.sryang.torang_repository.api.ApiReview
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var feedsViewModel: FeedsViewModel

    @Inject
    lateinit var remoteReviewService: ApiReview

    private val profileViewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var profileRepository: ProfileRepository

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorangScreen(
                lifecycleOwner = this@MainActivity,
                feedsViewModel = feedsViewModel,
                remoteReviewService = remoteReviewService,
                loginViewModel = loginViewModel,
                profileViewModel = profileViewModel,
                profileUrl = "http://sarang628.iptime.org:89/profile_images/"
            )
        }
    }
}
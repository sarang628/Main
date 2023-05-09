package com.example.screen_main.di

import android.content.Context
import android.view.View
import com.example.navigation.FeedNavigations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class FeedNavigationImpl {
    @Provides
    fun a(): FeedNavigations {
        return object : FeedNavigations {
            override fun goAddReview(context: Context, view: View) {

            }

            override fun goComment(context: Context, view: View) {

            }

            override fun goFullImage(context: Context, view: View) {

            }

            override fun goProfile(context: Context, view: View) {

            }

            override fun goRestaurant(context: Context, view: View) {

            }

        }
    }
}
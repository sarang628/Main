package com.example.screen_main

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import com.example.torang_core.navigation.MainNavigation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

class MainNavigationImpl @Inject constructor() : MainNavigation {
    override fun goMain(fragmentManager: FragmentManager?) {
        TODO("Not yet implemented")
    }

    override fun goMain(context: Context) {
        context.startActivity(Intent(context, MainActivity::class.java))
    }
}

@Module
@InstallIn(ActivityComponent::class)
abstract class MainNavigationModule {
    @Binds
    abstract fun bindAnalyticsService(
        mainNavigationImpl: MainNavigationImpl
    ): MainNavigation
}
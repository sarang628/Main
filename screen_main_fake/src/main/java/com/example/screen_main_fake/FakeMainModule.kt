package com.example.screen_main_fake

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.torang_core.navigation.MainNavigation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject


class FakeMainNavigationImpl @Inject constructor() : MainNavigation {
    override fun goMain(fragmentManager: FragmentManager?) {
        TODO("Not yet implemented")
    }

    override fun goMain(context: Context) {
        Toast.makeText(context, "fake navigation", Toast.LENGTH_SHORT).show()
    }

}

@Module
@InstallIn(ActivityComponent::class)
abstract class MainNavigationModule {
    @Binds
    abstract fun bindAnalyticsService(
        mainNavigationImpl: FakeMainNavigationImpl
    ): MainNavigation
}
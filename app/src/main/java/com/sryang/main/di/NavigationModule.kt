package com.sryang.main.di

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.torang_core.navigation.LoginNavigation
import com.example.torang_core.navigation.RestaurantDetailNavigation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationModule {
    @Binds
    abstract fun provide(dummyRestaurantDetailNavigation: DummyRestaurantDetailNavigation): RestaurantDetailNavigation
}


class DummyRestaurantDetailNavigation @Inject constructor() : RestaurantDetailNavigation {
    override fun go(context: Context, restaurantId: Int) {

    }
}

@Module
@InstallIn(ActivityComponent::class)
abstract class TestDependencies {
    @Binds
    abstract fun provideLoginNavigation(testLoginNavigation: TestLoginNavigation): LoginNavigation
}

class TestLoginNavigation @Inject constructor() : LoginNavigation {
    override fun goLogin(fragmentManager: FragmentManager?) {

    }

    override fun goLogin(context: Context) {

    }
}
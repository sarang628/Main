package com.sryang.main.di

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.torang_core.data.model.Feed
import com.example.torang_core.dialog.FeedDialogEventAdapter
import com.example.torang_core.dialog.FeedMyDialogEventAdapter
import com.example.torang_core.dialog.NotLoggedInFeedDialogEventAdapter
import com.example.torang_core.navigation.*
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

    @Binds
    abstract fun provideLoginNavigation(testLoginNavigation: TestLoginNavigation): LoginNavigation

    @Binds
    abstract fun provideMenuBottomSheetNavigation(testMenuBottomSheetNavigation: TestMenuBottomSheetNavigation): MenuBottomSheetNavigation

    @Binds
    abstract fun provideMyMenuBottomSheetNavigation(testMyMenuBottomSheetNavigation: TestMyMenuBottomSheetNavigation): MyMenuBottomSheetNavigation

    @Binds
    abstract fun provideNotLoggedInMenuBottomSheetNavigation(
        testNotLoggedInMenuBottomSheetNavigation: TestNotLoggedInMenuBottomSheetNavigation
    ): NotLoggedInMenuBottomSheetNavigation

    @Binds
    abstract fun provideTestReportNavigation(
        testReportNavigation: TestReportNavigation
    ): ReportNavigation

    @Binds
    abstract fun provideTestSettingsNavigation(
        testSettingsNavigation: TestSettingsNavigation
    ): SettingsNavigation
}


class DummyRestaurantDetailNavigation @Inject constructor() : RestaurantDetailNavigation {
    override fun go(context: Context, restaurantId: Int) {

    }
}

class TestLoginNavigation @Inject constructor() : LoginNavigation {
    override fun goLogin(fragmentManager: FragmentManager?) {

    }

    override fun goLogin(context: Context) {

    }
}

class TestMenuBottomSheetNavigation @Inject constructor() : MenuBottomSheetNavigation {
    override fun dismiss() {

    }

    override fun show(context: Context, dialogEventAdapter: FeedDialogEventAdapter, feed: Feed) {

    }

}

class TestMyMenuBottomSheetNavigation @Inject constructor() : MyMenuBottomSheetNavigation {
    override fun dismiss() {

    }

    override fun show(
        context: Context,
        myDialogEventAdapter: FeedMyDialogEventAdapter,
        feed: Feed
    ) {

    }
}

class TestNotLoggedInMenuBottomSheetNavigation @Inject constructor() :
    NotLoggedInMenuBottomSheetNavigation {
    override fun dismiss() {

    }

    override fun show(
        context: Context,
        myDialogEventAdapter: NotLoggedInFeedDialogEventAdapter,
        feed: Feed
    ) {

    }

}

class TestReportNavigation @Inject constructor() : ReportNavigation {
    override fun goReport(context: Context, reviewId: Int) {

    }
}

class TestSettingsNavigation @Inject constructor() : SettingsNavigation {
    override fun goSettings(context: Context) {

    }
}
package com.example.screen_main

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.navigation.AddReviewNavigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class NavigationModule {
    @Provides
    fun navigate(): AddReviewNavigation {
        return object : AddReviewNavigation {
            override fun navigate(fragment: Fragment) {
                fragment.requireActivity().findNavController(R.id.fc).navigate(R.id.action_mainFragment_to_myReviewFragment)
            }
        }
    }
}

package com.posco.feedscreentestapp.di

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import com.example.torang_core.data.model.User
import com.example.torang_core.login.LoginManager
import com.example.torang_core.login.OnLoginResultListener
import com.example.torang_core.login.OnResultLoginListener
import com.example.torang_core.login.OnResultLogoutListener
import com.example.torang_core.navigation.LoginNavigation
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {
    @Binds
    abstract fun provideLoginMudule(torangLoginManager: TorangLoginManager): LoginManager
}

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesScopesModule {

    @Singleton // Provide always the same instance
    @Provides
    fun providesCoroutineScope(): CoroutineScope {
        // Run this code when providing an instance of CoroutineScope
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
}

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class FacebookLoginProviderModule {
//@Binds
//abstract fun provideFacebookLoginProvider(facebookLoginProviderImpl: FacebookLoginProviderImpl): FacebookLoginProvider
/*
@Binds
abstract fun provideFacebookLoginProviderForView(faceBookLoginProviderForViewImpl: FacebookLoginProviderImpl) : FaceBookLoginProviderForView

@Binds
abstract fun provideFacebookLoginProviderForRepository(FacebookLoginProviderImpl: FacebookLoginProviderImpl) : FaceBookLoginProviderForRepository*/
//}

@Module
@InstallIn(ActivityComponent::class)
abstract class LoginNavigationModule {
    @Binds
    abstract fun provideLoginNavigation(loginNavigationImpl: LoginNavigationImpl): LoginNavigation
}

class LoginNavigationImpl @Inject constructor() : LoginNavigation {
    override fun goLogin(fragmentManager: FragmentManager?) {
        TODO("Not yet implemented")
    }

    override fun goLogin(context: Context) {
        Toast.makeText(context, "goLogin", Toast.LENGTH_SHORT).show()
    }
}

class TorangLoginManager : LoginManager{
    override fun isLogin(): LiveData<Boolean> {
        TODO("Not yet implemented")
    }

    override fun isLogin(context: Context): Boolean {
        TODO("Not yet implemented")
    }

    override fun loadUser(context: Context): User {
        TODO("Not yet implemented")
    }

    override fun logout(context: Context) {
        TODO("Not yet implemented")
    }

    override fun logout(onResultLogoutListener: OnResultLogoutListener) {
        TODO("Not yet implemented")
    }

    override fun onActivityResult(
        appCompatActivity: AppCompatActivity,
        requestCode: Int,
        resultCode: Int,
        data: Intent
    ) {
        TODO("Not yet implemented")
    }

    override fun onCreate(appCompatActivity: AppCompatActivity) {
        TODO("Not yet implemented")
    }

    override fun onDestroy(appCompatActivity: AppCompatActivity) {
        TODO("Not yet implemented")
    }

    override fun requestFacebookLogin(
        activity: AppCompatActivity,
        onLoginResultListener: OnLoginResultListener
    ) {
        TODO("Not yet implemented")
    }

    override fun requestKakaoLogin(onResultLoginListener: OnResultLoginListener) {
        TODO("Not yet implemented")
    }

}
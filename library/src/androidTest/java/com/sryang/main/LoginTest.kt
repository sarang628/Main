package com.sryang.main

import androidx.compose.runtime.Composable
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sryang.library.LoginLogic
import com.sryang.library.LoginResult
import com.sryang.library.LogoutResult
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginTest {

    @Test
    fun loginTest() {
        val logicLogic = object : LoginLogic {
            @Composable
            override fun LoginScreen() {
                TODO("Not yet implemented")
            }

            override suspend fun requestLogin(
                email: String,
                password: String,
                loginResult: LoginResult
            ) {
                TODO("Not yet implemented")
            }

            override suspend fun requestLogout(logoutResult: LogoutResult) {
                TODO("Not yet implemented")
            }
        }

        /*logicLogic.requestLogout {

        }*/
    }
}
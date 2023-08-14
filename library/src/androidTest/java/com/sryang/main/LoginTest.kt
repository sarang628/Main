package com.sryang.main

import com.sryang.library.LoginLogic
import com.sryang.library.LoginResult
import com.sryang.library.LogoutResult


class LoginTest {
    fun loginTest() {
        val logicLogic = object : LoginLogic {
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
    }
}
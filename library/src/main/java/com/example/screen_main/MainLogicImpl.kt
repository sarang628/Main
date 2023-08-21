package com.example.screen_main

import android.util.Log
import com.sryang.library.MainLogic

class MainLogicImpl : MainLogic {

    override fun checkLogin(): Boolean {
        return false
    }

    override fun goLoginScreen() {
        Log.d("__sryang", "goLoginScreen")
        TODO("로그인 화면 이동 기능 구현 안됨")
    }
}
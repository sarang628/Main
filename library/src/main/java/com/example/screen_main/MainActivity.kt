package com.example.screen_main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.screen_main.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * [ActivityMainBinding]
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //바인딩
//    lateinit var loginManager: LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        loginManager.onActivityResult(this, requestCode, resultCode, data!!)
    }

    override fun onDestroy() {
        super.onDestroy()
//        loginManager.onDestroy(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        Logger.d("onOptionsItemSelected")
        return super.onOptionsItemSelected(item)
    }
}

package com.sryang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sryang.torang.compose.MainScreen
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.alarm.compose.AlarmScreen
import com.sryang.main.BuildConfig
import com.sryang.screenfindingtest.di.finding.Finding
import com.sryang.torang.compose.bottomsheet.comment.CommentBottomSheetDialog
import com.sryang.torang.di.feed_di.FeedScreen
import com.sryang.torang.di.main.ProvideMainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Displaying edge-to-edge
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            TorangTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProvideMainScreen(rememberNavController())
                }
            }
        }
    }
}
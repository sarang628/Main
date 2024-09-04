package com.sarang.torang.compose.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
object Feed {
    override fun toString(): String {
        return "com.sarang.torang.compose.main.Feed"
    }
}

@Serializable
object Finding {
    override fun toString(): String {
        return "com.sarang.torang.compose.main.Finding"
    }
}

@Serializable
object Add {
    override fun toString(): String {
        return "com.sarang.torang.compose.main.Add"
    }
}

@Serializable
object Alarm {
    override fun toString(): String {
        return "com.sarang.torang.compose.main.Alarm"
    }
}

@Serializable
object Profile {
    override fun toString(): String {
        return "com.sarang.torang.compose.main.Profile"
    }
}

val items = listOf(
    Feed,
    Finding,
    Add,
    Alarm,
    Profile
)


val Any.icon: ImageVector
    get() =
        when (this) {
            is Feed -> Icons.Filled.Home
            is Finding -> Icons.Filled.Search
            is Add -> Icons.Filled.AddCircle
            is Alarm -> Icons.Filled.Notifications
            is Profile -> Icons.Filled.Face
            else -> {
                Icons.Filled.Home
            }
        }
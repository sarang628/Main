package com.sarang.torang.compose.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
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
object FeedGrid {
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
object FindingMap {
    override fun toString(): String {
        return "com.sarang.torang.compose.main.FindingMap"
    }
}

@Serializable
object Profile {
    override fun toString(): String {
        return "com.sarang.torang.compose.main.Profile"
    }
}

val mainNavigations = listOf(
    Feed,
    FeedGrid,
    Add,
    FindingMap,
    Profile
)


val Any.icon: ImageVector
    get() =
        when (this) {
            is Feed -> Icons.Filled.Home
            is FeedGrid -> Icons.Filled.Search
            is Add -> Icons.Filled.AddCircle
            is FindingMap -> Icons.Filled.LocationOn
            is Profile -> Icons.Filled.Face
            else -> {
                Icons.Filled.Home
            }
        }
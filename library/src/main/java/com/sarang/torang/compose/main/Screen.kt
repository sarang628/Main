package com.sarang.torang.compose.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.sarang.torang.navigation.Add
import com.sarang.torang.navigation.Feed
import com.sarang.torang.navigation.FeedGrid
import com.sarang.torang.navigation.Find
import com.sarang.torang.navigation.Profile
import kotlin.reflect.KClass

enum class MainDestination(
    val icon        : ImageVector,
    val route       : KClass<*>,
    val baseRoute   : KClass<*> = route
) {
    FEED(
        icon = Icons.Filled.Home,
        route = Feed::class
    ),
    FEED_GRID(
        icon = Icons.Filled.Search,
        route = FeedGrid::class
    ),
    ADD(
        icon = Icons.Filled.AddCircle,
        route = Add::class
    ),
    FIND(
        icon = Icons.Filled.LocationOn,
        route = Find::class
    ),
    PROFILE(
        icon = Icons.Filled.Face,
        route = Profile::class
    )
}
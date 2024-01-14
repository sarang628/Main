package com.sarang.torang.di.finding

import com.example.screen_finding.data.RestaurantInfo
import com.example.screen_finding.viewmodel.Filter
import com.sryang.torang_repository.data.remote.response.RemoteRestaurant


fun String.toBoundary(): Double {
    if (this.equals("100m")) {
        return 100.0
    } else if (this.equals("300m")) {
        return 300.0
    } else if (this.equals("500m")) {
        return 500.0
    } else if (this.equals("1km")) {
        return 1000.0
    } else if (this.equals("3km")) {
        return 3000.0
    }
    return 0.0
}

fun Filter.toFilter(): com.sryang.torang_repository.data.Filter {
    return com.sryang.torang_repository.data.Filter(
        restaurantTypes = this.restaurantTypes?.map { it.uppercase() },
        prices = this.prices,
        ratings = this.ratings?.toRating(),
        distances = this.distances?.toDistnace(),
        lat = this.lat,
        lon = this.lon,
        north = this.north,
        south = this.south,
        east = this.east,
        west = this.west
    )
}

fun String.toDistnace(): String? {
    if (this == "100m")
        return "_100M"
    else if (this == "300m")
        return "_300M"
    else if (this == "500m")
        return "_500M"
    else if (this == "1km")
        return "_1KM"
    else if (this == "3km")
        return "_3KM"
    return null
}

fun List<String>.toRating(): List<String>? {
    return this.map {
        if (it == "*")
            "ONE"
        else if (it == "**")
            "TWO"
        else if (it == "***")
            "THREE"
        else if (it == "****")
            "FOUR"
        else if (it == "*****")
            "FIVE"
        else
            ""
    }
}

fun RemoteRestaurant.toRestaurantInfo(): RestaurantInfo {
    return RestaurantInfo(
        restaurantId = this.restaurantId,
        restaurantName = this.restaurantName,
        rating = this.rating,
        foodType = this.restaurantType,
        restaurantImage = this.imgUrl1,
        price = "$$$",
        distance = "120m",
        lat = this.lat,
        lon = this.lon,
    )
}


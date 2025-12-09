package com.sarang.torang

import androidx.navigation.NavHostController

class RootNavController(val navController: NavHostController? = null) {
    fun modReview(): (Int) -> Unit = { }

    fun imagePager(reviewId: Int, position: Int) {

    }

    fun emailLogin() {

    }

    fun restaurant(id: Int) {
        navController?.navigate("restaurant/${id}")
    }

    fun settings() {

    }

    fun popBackStack() {

    }

    fun like(id: Int) {

    }

    fun main() {

    }

    fun restaurantImagePager(it: Int) {

    }

    fun addReview() {

    }

    fun splash() {

    }

    fun editProfileImage() {

    }

    fun singleTopLogin() {

    }

    fun singleTopMain() {

    }

    fun review(it: Int) {

    }

    fun profile(it: Int) {

    }

    fun goAlarm() {

    }

    fun map(it: Int) {}
    fun myReview(it: Int) {

    }
}
package com.example.screen_main

import android.location.Location
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    //loginRepository: LoginRepository
) :
    ViewModel() {
//    val restaurants = MutableLiveData<ArrayList<Restaurant>?>()
    val clickMenu = MutableLiveData<Boolean>()
    val searchQuery = MutableLiveData<String>()
    val currentMenuId = MutableLiveData<Int>()
//    val loggedInUser = loginRepository.getLoginUser()
    private val _backPressedFlag = MutableLiveData<Boolean>().apply {
        value = false
    }
    val backPressedFlag: LiveData<Boolean> = _backPressedFlag

    private val empty = MutableLiveData<Boolean>().apply {
        value = false
    }

    fun onBackPressed() {
        viewModelScope.launch {
            _backPressedFlag.value = true
            delay(3000)
            _backPressedFlag.value = false
        }

    }

    fun setClickMenu(clickMenu: Boolean) {
        this.clickMenu.value = clickMenu
    }

    fun loadRestaurant(
        //filter: Filter, location: Location?, progress: View?
    ) {
//        if (progress != null) progress.visibility = View.VISIBLE
//        if (location != null) {
//            filter.lat = (location.latitude)
//            filter.lon = (location.longitude)
//        }
    }

    fun setRestaurants(
        //restaurants: ArrayList<Restaurant>?
    ) {
//        this.restaurants.value = restaurants
    }

    fun setSearchQuery(searchQuery: String) {
        this.searchQuery.value = searchQuery
    }

    fun getPositionByRestaurantName(title: String): Int {
        val position = 0
//        if (restaurants.value != null) {
//            val list = restaurants.value
//            for (i in list!!.indices) {
//                if (list[i].restaurant_name == title) return i
//            }
//        }
        return position
    }

    fun clickBottomMenu(menuItem: MenuItem) {
        currentMenuId.value = menuItem.itemId
    }

    fun clickBottomMenu() {}
    fun setCurrentMenuId(currentMenuId: Int) {
        this.currentMenuId.value = currentMenuId
    }

    private fun emptyCheck() {
        //TODO::통신실패하거나 리스트 비어있을 시 빈화면 처리하기
        /*if (restaurants.value == null || restaurants.value!!.size <= 0) {
            empty.setValue(true)
        } else {
            empty.setValue(false)
        }*/
    }
}
package com.sryang.torang_repository.di.service.restaurant

import com.sryang.torang_repository.api.ApiRestaurant
import com.sryang.torang_repository.di.RetrofitModule
import com.sryang.torang_repository.di.TorangOkhttpClient
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 토랑 서비스
 */
@Singleton
class ProductRestaurantService @Inject constructor(
    private val torangOkHttpClientImpl: TorangOkhttpClient,
    private val retrofitModule: RetrofitModule
) {
    private var url = "https://www.vrscoo.com:8080/"
    fun create(): ApiRestaurant {
        return retrofitModule.getRetrofit(torangOkHttpClientImpl.getHttpClient(), url).create(
            ApiRestaurant::class.java
        )
    }
}
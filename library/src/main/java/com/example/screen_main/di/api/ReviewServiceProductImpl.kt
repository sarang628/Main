package com.sryang.torang_repository.di.api

import com.sryang.torang_repository.di.RetrofitModule
import com.sryang.torang_repository.di.TorangOkhttpClient
import com.sryang.torang_repository.api.ApiReview
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 리뷰 서비스 Product
 */
@Singleton
class ReviewServiceProductImpl @Inject constructor(
    private val torangOkHttpClientImpl: TorangOkhttpClient,
    private val retrofitModule: RetrofitModule
) {
    private var url = "http://sarang628.iptime.org:8081/"
    fun create(): ApiReview {
        return retrofitModule
//            .getRetrofit(torangOkHttpClientImpl.getUnsafeOkHttpClient(), url)
            .getRetrofit(torangOkHttpClientImpl.getHttpClient(), url)
            .create(ApiReview::class.java)
    }
}
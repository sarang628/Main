package com.posco.feedscreentestapp.di.service

import android.content.Context
import com.sryang.torang_repository.di.RetrofitModule
import com.sryang.torang_repository.di.TorangOkhttpClient
import com.sryang.torang_repository.repository.preference.TorangPreference
import com.sryang.torang_repository.services.ReportService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


/**
 * 실서버 신고 서비스
 */
@Singleton
class ProductReportService @Inject constructor(
    private val torangOkHttpClientImpl: TorangOkhttpClient,
    private val retrofitModule: RetrofitModule
) {
    private var url = "http://sarang628.iptime.org:8080/"
//    private var url = "https://www.vrscoo.com:8080/"
    fun create(): ReportService {
        return retrofitModule.getRetrofit(torangOkHttpClientImpl.getHttpClient(), url).create(
            ReportService::class.java
        )
    }
}

/**
 * 로컬 서버 신고 서비스
 */
class LocalReportService {

    var API_URL = "http://10.0.2.2:8080/"

    private fun getHttpClient(context: Context): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.HEADERS
        logger.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logger)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)
        httpClient.readTimeout(10, TimeUnit.SECONDS)

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("User-Agent", "android")
                .header(
                    "accessToken",
                    TorangPreference().getAccessToken(context)!!
                )
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }

        return httpClient.build()
    }

    private fun getRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun create(context: Context): ReportService {
        return getRetrofit(getHttpClient(context = context)).create(ReportService::class.java)
    }
}

@InstallIn(SingletonComponent::class)
@Module
class ReportServiceModule {

    @Singleton
    @Provides
    fun provideReportService(productReportService: ProductReportService): ReportService {
        return productReportService.create()
    }
}

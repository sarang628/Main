package com.sryang.findinglinkmodules.di.filter

import com.sarang.torang.api.ApiFilter
import com.sarang.torang.data.remote.response.RemoteCity
import com.sryang.screen_filter.data.City
import com.sryang.screen_filter.usecase.GetCitiesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetCitiesUseCaseImpl {
    @Provides
    fun providesGetCitiesUseCase(apiFilter: ApiFilter): GetCitiesUseCase {
        return object : GetCitiesUseCase {
            override suspend fun invoke(): List<City> {
                return apiFilter.getCities().map { it.toCity() }
            }
        }
    }
}

fun RemoteCity.toCity(): City {
    return City(
        name = this.name,
        latitude = this.latitude,
        longitude = this.longitude,
        url = this.url,
        zoom = this.zoom
    )
}
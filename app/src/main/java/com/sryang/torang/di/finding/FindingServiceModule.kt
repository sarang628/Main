package com.sryang.screenfindingtest.di.finding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.screen_finding.data.RestaurantInfo
import com.example.screen_finding.ui.FindScreen
import com.example.screen_finding.usecase.FindRestaurantUseCase
import com.example.screen_finding.usecase.SearchThisAreaUseCase
import com.example.screen_finding.viewmodel.Filter
import com.example.screen_finding.viewmodel.FindingViewModel
import com.google.maps.android.compose.rememberCameraPositionState
import com.sryang.torang.di.finding.toFilter
import com.sryang.torang_repository.api.ApiRestaurant
import com.sryang.torang_repository.data.SearchType
import com.sryang.torang_repository.repository.MapRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Composable
fun Finding(
    findingViewModel: FindingViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val uiState by findingViewModel.uiState.collectAsState()
    val cameraPositionState = rememberCameraPositionState()
    val coroutineScope = rememberCoroutineScope()
    var isMovingByMarkerClick by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(true) }
    FindScreen(
        restaurantCardPage = {

        },
        mapScreen = {

        },
        onZoomIn = {
        },
        onZoomOut = {

        },
        filter = {

        },
        myLocation = {

        }

    )
}


@InstallIn(SingletonComponent::class)
@Module
class FindingServiceModule {
    @Provides
    fun provideFindingService(apiRestaurant: ApiRestaurant): FindRestaurantUseCase {
        return object : FindRestaurantUseCase {
            override suspend fun findRestaurants(): List<RestaurantInfo> {
                /*return apiRestaurant.getAllRestaurant(HashMap()).stream().map {
                    it.toRestaurantInfo()
                }.toList()*/
                return listOf()
            }

            override suspend fun filter(filter: Filter): List<RestaurantInfo> {
                /*return apiRestaurant.getFilterRestaurant(
                    filter = filter.toFilter()
                )
                    .stream().map {
                        it.toRestaurantInfo()
                    }.toList()*/
                return listOf()
            }
        }
    }

    @Provides
    fun provideSearchThisAreaModule(
        apiRestaurant: ApiRestaurant,
        mapRepository: MapRepository
    ): SearchThisAreaUseCase {
        return object : SearchThisAreaUseCase {
            override suspend fun invoke(filter: Filter): List<RestaurantInfo> {

                val filter = filter.toFilter()
                filter.north = mapRepository.getNElon()
                filter.east = mapRepository.getNElat()
                filter.south = mapRepository.getSWlon()
                filter.west = mapRepository.getSWlat()
                filter.searchType = SearchType.BOUND

                //return apiRestaurant.getFilterRestaurant(filter = filter).map { it.toRestaurantInfo() }
                return listOf()
            }
        }
    }
}

package com.manohar.myweather.framework.datasource.network.implementation

import com.manohar.myweather.business.domain.postal.PinResponse
import com.manohar.myweather.business.domain.weather.WeatherResponse
import com.manohar.myweather.framework.datasource.apiservice.ApiService
import com.manohar.myweather.framework.datasource.apiservice.WeatherApiService
import com.manohar.myweather.framework.datasource.network.abstraction.StateAndDistrictNetworkService
import com.manohar.myweather.framework.datasource.network.abstraction.WeatherNetworkService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherNetworkServiceImpl @Inject constructor(
    private val apiService: WeatherApiService
) : WeatherNetworkService {


    override suspend fun getWeatherData(city: String): Response<WeatherResponse> {
        return apiService.getWeatherData(city)
    }


}
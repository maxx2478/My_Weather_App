package com.manohar.myweather.business.data.network.implementation

import com.manohar.myweather.business.data.network.abstraction.StateAndDistrictNetworkDataSource
import com.manohar.myweather.business.data.network.abstraction.WeatherNetworkDataSource
import com.manohar.myweather.business.domain.postal.PinResponse
import com.manohar.myweather.business.domain.weather.WeatherResponse
import com.manohar.myweather.framework.datasource.network.abstraction.StateAndDistrictNetworkService
import com.manohar.myweather.framework.datasource.network.abstraction.WeatherNetworkService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherNetworkDataSourceImpl  @Inject constructor(private val weatherNetworkService: WeatherNetworkService) :
    WeatherNetworkDataSource {


    override suspend fun getWeatherData(city: String): Response<WeatherResponse> {
       return weatherNetworkService.getWeatherData(city)
    }


}
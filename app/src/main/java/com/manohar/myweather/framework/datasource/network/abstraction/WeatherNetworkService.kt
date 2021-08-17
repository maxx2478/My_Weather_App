package com.manohar.myweather.framework.datasource.network.abstraction

import com.manohar.myweather.business.domain.weather.WeatherResponse
import retrofit2.Response

interface WeatherNetworkService {

    suspend fun getWeatherData(city:String): Response<WeatherResponse>


}
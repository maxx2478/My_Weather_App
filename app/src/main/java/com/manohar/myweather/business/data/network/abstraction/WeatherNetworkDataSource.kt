package com.manohar.myweather.business.data.network.abstraction

import com.manohar.myweather.business.domain.weather.WeatherResponse
import retrofit2.Response

interface WeatherNetworkDataSource {

    suspend fun getWeatherData(city:String): Response<WeatherResponse>


}
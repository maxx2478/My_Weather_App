package com.manohar.myweather.framework.datasource.apiservice

import com.manohar.myweather.business.domain.postal.PinResponse
import com.manohar.myweather.business.domain.weather.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApiService {

    @GET("current.json?key=35c9f92ac5bf4df0811144140212307")
    suspend fun getWeatherData(
        @Query("q") city: String?
    ): Response<WeatherResponse>

}
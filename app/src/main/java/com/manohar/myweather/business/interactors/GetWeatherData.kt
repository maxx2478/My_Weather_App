package com.manohar.myweather.business.interactors

import com.manohar.myweather.business.data.network.abstraction.StateAndDistrictNetworkDataSource
import com.manohar.myweather.business.data.network.abstraction.WeatherNetworkDataSource
import com.manohar.myweather.business.data.utils.ApiResponseHandler
import com.manohar.myweather.business.data.utils.NetworkUtils
import com.manohar.myweather.business.data.utils.Resource
import com.manohar.myweather.business.domain.postal.PinResponse
import com.manohar.myweather.business.domain.weather.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class GetWeatherData @Inject constructor(
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) {

     fun invoke(city:String): Flow<Resource<WeatherResponse?>>
    {
        return flow {
            emit(Resource.loading())

            val apiResult = NetworkUtils.safeApiCall(Dispatchers.IO) {
                weatherNetworkDataSource.getWeatherData(city)
            }
            val resource = object : ApiResponseHandler<Response<WeatherResponse>, WeatherResponse>(apiResult) {
                override suspend fun handleSuccess(resultObj: Response<WeatherResponse>): Resource<WeatherResponse?> {
                    return if (resultObj.isSuccessful) {
                        Resource.success(resultObj.body())
                    } else {
                        Resource.error(Throwable(resultObj.errorBody().toString()))
                    }
                }
            }.getResult()

            emit(resource)
        }
    }

}

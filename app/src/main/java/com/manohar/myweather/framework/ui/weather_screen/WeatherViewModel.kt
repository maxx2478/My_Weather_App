package com.manohar.myweather.framework.ui.weather_screen

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.manohar.myweather.business.data.utils.Status
import com.manohar.myweather.business.domain.weather.WeatherResponse
import com.manohar.myweather.business.interactors.GetStateAndDistrict
import com.manohar.myweather.business.interactors.GetWeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    val getWeatherData: GetWeatherData
)  : ViewModel() {

    val city = MutableLiveData<String>()
    var data = MutableLiveData<WeatherResponse?>()
    var temp_c = MutableLiveData<String>().apply { value = "Current Temperature in Centigrade:" }
    var temp_f = MutableLiveData<String>().apply { value = "Current Temperature in Fahrenheit:" }
    var lat = MutableLiveData<String>().apply { value = "Latitude: " }
    var lon = MutableLiveData<String>().apply { value = "Longitude: " }


    fun getWeatherData(city:String)
    {

        viewModelScope.launch {

            getWeatherData.invoke(city).collect {

                if (it.status == Status.SUCCESS) {

                    if (it.data!=null)
                    {
                        data.value = it.data!!
                        temp_c.value = "Current Temperature in Centigrade: " + data?.value?.current?.temp_c.toString()
                        temp_f.value = "Current Temperature in Fahrenheit:" + data?.value?.current?.temp_f.toString()
                        lat.value = "Latitude: " + data?.value?.location?.lat.toString()
                        lon.value = "Longitude: "  + data?.value?.location?.lon.toString()
                        data.value?.current?.last_updated?.let { it1 -> Log.i("weatherData", it1) }

                    }
                    else
                        data.value = null

                } else if (it.status == Status.ERROR) {
                    data.value = null
                }
            }
        }



    }



}
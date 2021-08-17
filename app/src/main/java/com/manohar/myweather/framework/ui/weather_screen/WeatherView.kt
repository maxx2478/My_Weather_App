package com.manohar.myweather.framework.ui.weather_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.manohar.myweather.R
import com.manohar.myweather.databinding.ActivityMainBinding
import com.manohar.myweather.databinding.ActivityWeatherViewBinding
import com.manohar.myweather.framework.datasource.utils.observeOnce
import com.manohar.myweather.framework.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class WeatherView : AppCompatActivity() {


    lateinit var binding: ActivityWeatherViewBinding
    private val viewModel: WeatherViewModel by viewModels()
    var showToastOnlyOnce = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherViewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.lifecycleOwner = this
        binding.mainviewmodel = viewModel

        val actionBar = supportActionBar
        actionBar?.hide()


        viewModel.city.observe(this, {
            binding.showRes.isEnabled = !it.isNullOrEmpty()
        })

        binding.showRes.setOnClickListener {
            showToastOnlyOnce = true

                viewModel.getWeatherData(city = viewModel.city.value.toString())
                viewModel.data.observe(this, {

                    if (it==null)
                    {
                        if (showToastOnlyOnce)
                        {
                            showToastOnlyOnce = false
                            Toast.makeText(this, "Invalid City", Toast.LENGTH_SHORT).show()

                        }
                    }
                })




        }


    }
}
package com.manohar.myweather.business.domain.weather

data class WeatherResponse(
    val current: Current,
    val location: Location
)
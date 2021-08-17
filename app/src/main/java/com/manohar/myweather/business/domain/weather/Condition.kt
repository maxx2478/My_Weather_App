package com.manohar.myweather.business.domain.weather

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
)
package com.manohar.myweather.di

import javax.inject.Qualifier


    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class PostalRetrofit

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class WeatherRetrofit


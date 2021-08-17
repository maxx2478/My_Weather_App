package com.manohar.myweather.di

import com.manohar.myweather.business.data.network.abstraction.StateAndDistrictNetworkDataSource
import com.manohar.myweather.business.data.network.abstraction.WeatherNetworkDataSource
import com.manohar.myweather.business.data.network.implementation.StateAndDistrictNetworkDataSourceImpl
import com.manohar.myweather.business.data.network.implementation.WeatherNetworkDataSourceImpl
import com.manohar.myweather.framework.datasource.network.abstraction.StateAndDistrictNetworkService
import com.manohar.myweather.framework.datasource.network.abstraction.WeatherNetworkService
import com.manohar.myweather.framework.datasource.network.implementation.StateAndDistrictNetworkServiceImple
import com.manohar.myweather.framework.datasource.network.implementation.WeatherNetworkServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface NetworkDataSourceModule {

    @Binds
    fun provideStateAndDistrictNetworkService(
        stateAndDistrictNetworkServiceImple: StateAndDistrictNetworkServiceImple
    ): StateAndDistrictNetworkService

    @Binds
    fun provideLoginUserNetworkDataSource(
        stateAndDistrictNetworkDataSourceImpl: StateAndDistrictNetworkDataSourceImpl
    ): StateAndDistrictNetworkDataSource


    @Binds
    fun provideWeatherNetworkService(
        weatherNetworkServiceImpl: WeatherNetworkServiceImpl
    ): WeatherNetworkService

    @Binds
    fun provideWeatherNetworkDataSource(
        weatherNetworkDataSourceImpl: WeatherNetworkDataSourceImpl
    ): WeatherNetworkDataSource

}
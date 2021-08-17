package com.manohar.myweather.business.data.network.implementation

import com.manohar.myweather.business.data.network.abstraction.StateAndDistrictNetworkDataSource
import com.manohar.myweather.business.domain.postal.PinResponse
import com.manohar.myweather.framework.datasource.network.abstraction.StateAndDistrictNetworkService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StateAndDistrictNetworkDataSourceImpl  @Inject constructor(private val stateAndDistrictNetworkService: StateAndDistrictNetworkService) :
    StateAndDistrictNetworkDataSource {
    override suspend fun getStateAndDistrict(pincode: String): Response<PinResponse>? {

        return stateAndDistrictNetworkService.getStateAndDistrict(pincode)
    }


}
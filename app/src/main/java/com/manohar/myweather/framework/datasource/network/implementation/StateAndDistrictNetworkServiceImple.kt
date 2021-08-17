package com.manohar.myweather.framework.datasource.network.implementation

import com.manohar.myweather.business.domain.postal.PinResponse
import com.manohar.myweather.framework.datasource.apiservice.ApiService
import com.manohar.myweather.framework.datasource.network.abstraction.StateAndDistrictNetworkService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StateAndDistrictNetworkServiceImple @Inject constructor(
      private val apiService: ApiService
) : StateAndDistrictNetworkService  {
    override suspend fun getStateAndDistrict(pincode: String): Response<PinResponse>? {

        return apiService.getStateAndDistrict(pincode)
    }


}
package com.manohar.myweather.framework.datasource.network.abstraction

import com.manohar.myweather.business.domain.postal.PinResponse
import retrofit2.Response

interface StateAndDistrictNetworkService {

    suspend fun getStateAndDistrict(pincode: String): Response<PinResponse>?


}
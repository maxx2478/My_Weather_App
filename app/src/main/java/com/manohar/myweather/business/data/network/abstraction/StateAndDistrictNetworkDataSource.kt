package com.manohar.myweather.business.data.network.abstraction

import com.manohar.myweather.business.domain.postal.PinResponse
import retrofit2.Response

interface StateAndDistrictNetworkDataSource {

    suspend fun getStateAndDistrict(pincode: String): Response<PinResponse>?


}
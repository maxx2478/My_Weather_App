package com.manohar.myweather.framework.datasource.apiservice

import com.manohar.myweather.business.domain.postal.PinResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("pincode/{PINCODE}")
    suspend fun getStateAndDistrict(
        @Path("PINCODE") pincode: String?
    ): Response<PinResponse>



}
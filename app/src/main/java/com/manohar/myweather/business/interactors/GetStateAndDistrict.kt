package com.manohar.myweather.business.interactors

import com.manohar.myweather.business.data.network.abstraction.StateAndDistrictNetworkDataSource
import com.manohar.myweather.business.data.utils.ApiResponseHandler
import com.manohar.myweather.business.data.utils.NetworkUtils.Companion.safeApiCall
import com.manohar.myweather.business.data.utils.Resource
import com.manohar.myweather.business.domain.postal.PinResponse
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject


@ViewModelScoped
class GetStateAndDistrict @Inject constructor(
    private val stateAndDistrictNetworkDataSource: StateAndDistrictNetworkDataSource) {

    fun invoke(pinCode:String): Flow<Resource<PinResponse?>>
    {
        return flow {
            emit(Resource.loading())

            val apiResult = safeApiCall(Dispatchers.IO) {
                stateAndDistrictNetworkDataSource.getStateAndDistrict(pinCode)
            }
            val resource = object : ApiResponseHandler<Response<PinResponse>, PinResponse>(apiResult) {
                override suspend fun handleSuccess(resultObj: Response<PinResponse>): Resource<PinResponse?> {
                    return if (resultObj.isSuccessful && resultObj.body()?.get(0)?.Status == "Success") {
                        Resource.success(resultObj.body())
                    } else {
                        Resource.error(Throwable(resultObj.body()?.get(0)?.Message))
                    }
                }
            }.getResult()

            emit(resource)
        }
    }

}

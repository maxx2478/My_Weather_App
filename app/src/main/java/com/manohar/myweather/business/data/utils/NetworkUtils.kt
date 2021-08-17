package com.manohar.myweather.business.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*

class NetworkUtils {

    companion object{
        const val NETWORK_ERROR_UNKNOWN = "Unknown network error!"
        const val NETWORK_ERROR_TIMEOUT = "Network timeout"

        suspend fun <T> safeApiCall(
            dispatcher: CoroutineDispatcher,
            apiCall: suspend () -> T?
        ): ApiResult<T?> {
            return withContext(dispatcher) {
                try {
                    ApiResult.Success(apiCall.invoke())
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                    when (throwable) {
                        is SocketTimeoutException -> {
                            ApiResult.NetworkError("Timeout! Please check your internet connection or retry!")
                        }
                        is UnknownHostException -> {
                            ApiResult.NetworkError("You don't have a proper internet connection or server is not up")
                        }
                        is ConnectException -> {
                            ApiResult.NetworkError("You don't have a proper internet connection")
                        }
                        is IOException -> {
                            ApiResult.NetworkError("Some I/O error occurred!")
                        }
                        is HttpException -> {
                            val code = throwable.code()
                            val errorResponse = convertErrorBody(throwable)
                            Log.i("ApiExtension", "com.equinox.atlas.bde.business.data.network.utils.safeApiCall: $errorResponse ")
                            ApiResult.GenericError(
                                code,
                                errorResponse
                            )
                        }
                        else -> {
                            ApiResult.GenericError(
                                null,
                                NETWORK_ERROR_UNKNOWN
                            )
                        }
                    }
                }
            }
        }



        private fun convertErrorBody(throwable: HttpException): String? {
            return try {
                throwable.toString()
            } catch (exception: Exception) {
                "Unknown"
            }
        }



    }

}
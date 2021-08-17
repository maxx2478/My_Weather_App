package com.manohar.myweather.business.data.utils

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName




@Keep
data class ApiResponse<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("response")
    val response: Response
)

@Keep
data class ApiResponseX<T>(
    @SerializedName("states")
    val states: T?,
)

@Keep
data class ApiResponse2<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("response")
    val response: Response2
)

@Keep
data class ApiResponseList<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("response")
    val response: List<Response>
)

@Keep
data class ApiResponseDataObject<T>(
    @SerializedName("dataObject")
    val data: T,
    val code: String,
    val message: String
)

@Keep
data class ApiResponseDataObject2<T>(
    @SerializedName("code")
    val code: Int,
    @SerializedName("dataObject")
    val dataObject: T,
    @SerializedName("message")
    val message: String)

@Keep
data class Response(

    @SerializedName("CODE")
    val code: String,
    @SerializedName("MESSAGE")
    val message: String?,
    )

@Keep
data class Response2(

    @SerializedName("CODE")
    val code: Int,
    @SerializedName("MESSAGE")
    val message: String?,
)
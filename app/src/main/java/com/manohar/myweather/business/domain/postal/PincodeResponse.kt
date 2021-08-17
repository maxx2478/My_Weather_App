package com.manohar.myweather.business.domain.postal

data class PincodeResponse(
    val Message: String,
    val PostOffice: List<PostOffice>,
    val Status: String
)
package com.manohar.myweather.framework.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.manohar.myweather.business.data.utils.Status
import com.manohar.myweather.business.domain.postal.PostOffice
import com.manohar.myweather.business.interactors.GetStateAndDistrict
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val getStateAndDistrict: GetStateAndDistrict
) : ViewModel() {

    val phone = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val gender = MutableLiveData<String>()
    val dob = MutableLiveData<String>()
    val address1 = MutableLiveData<String>()
    val address2 = MutableLiveData<String>()
    val zipcode = MutableLiveData<String>()
    val district = MutableLiveData<String>()
    val state = MutableLiveData<String>()

    val isLoading = MutableLiveData<Boolean>()
    val isLoadingSuccess = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    val pinRemoteData = MutableLiveData<List<PostOffice>?>()
    val isDataValidx = MutableLiveData<Boolean>()


    fun isDataValid(): MutableLiveData<Boolean> {

        if (phone.value?.trim()?.isEmpty() == false
            && name.value?.trim()?.isEmpty() == false
            && gender.value?.trim()?.isEmpty() == false
            && dob.value?.trim()?.isEmpty() == false
            && address1.value?.trim()?.isEmpty() == false
            && zipcode.value?.trim()?.isEmpty() == false
            && district.value?.trim()?.isEmpty() == false
            && state.value?.trim()?.isEmpty() == false

        ) {
            if (phone.value?.trim()?.length!! >= 10 && name.value?.trim()?.length!! >= 3
                && address1.value?.trim()?.length!! >= 3 && zipcode.value?.trim()?.length!! >= 6

            ) {
                isDataValidx.value = true
            }
            else
            {
                isDataValidx.value = false
            }


        } else {
            isDataValidx.value = false
        }

        Log.i("validation", isDataValidx.value.toString())

        return isDataValidx


    }

    fun getStateDistrictData(pincode: String) {
        viewModelScope.launch {
            getStateAndDistrict.invoke(pincode).collect {
                isLoading.value = true
                if (it.status == Status.SUCCESS) {
                    isLoadingSuccess.value = true
                    isLoading.value = false
                    pinRemoteData.value = it.data?.get(0)?.PostOffice
                    state.value = it.data?.get(0)?.PostOffice?.get(0)?.State
                    district.value = it.data?.get(0)?.PostOffice?.get(0)?.District

                } else if (it.status == Status.ERROR) {
                    pinRemoteData.value = null
                    state.value = ""
                    district.value = ""
                    isLoading.value = false
                    isLoadingSuccess.value = false
                    error.value = it.error?.message
                }
            }
        }
    }


}

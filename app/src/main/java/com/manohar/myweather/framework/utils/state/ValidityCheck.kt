package com.manohar.myweather.framework.ui.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

abstract class ValidityCheck {
    protected open val _isValid = MediatorLiveData<Boolean>()

    val isValid: LiveData<Boolean>
        get() = _isValid

    abstract fun checkValidity(): Boolean
}
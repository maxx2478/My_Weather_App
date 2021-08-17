package com.manohar.myweather.framework.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.manohar.myweather.framework.ui.utils.EmailTextFieldState
import com.manohar.myweather.framework.ui.utils.MobileFieldState
import com.manohar.myweather.framework.ui.utils.TextFieldState

class addUserFormState(
    val mobileFieldState: MobileFieldState,
    val fullname: NameField,
    val gender: NameField,
    val dob: DOBField,
    val addressline1: NameField,
    val addressline2: NameField,
    val pincode: ZipField,

    ) {

    private val _isValid = MediatorLiveData<Boolean>().apply {

        addSource(mobileFieldState.text) {
            value = validateFields()
        }
        addSource(fullname.text) {
            value = validateFields()
        }
        addSource(gender.text) {
            value = validateFields()
        }
        addSource(dob.text) {
            value = validateFields()
        }
        addSource(addressline1.text) {
            value = validateFields()
        }
        addSource(addressline2.text) {
            value = validateFields()
        }
        addSource(pincode.text) {
            value = validateFields()
        }
    }

    val isValid: LiveData<Boolean>
        get() = _isValid


    private fun validateFields(): Boolean {
        return mobileFieldState.isPatternValid()
                && fullname.isPatternValid()
                && gender.isPatternValid()
                && dob.isPatternValid()
                && addressline1.isPatternValid()
                && addressline2.isPatternValid()
                && pincode.isPatternValid()

    }

}

class ZipField(
    private val errorMessage: String = "",
    private val minLength: Int = 6,
    defaultValue: String = "",
    private val isMandatory: Boolean = true
) : TextFieldState(defaultValue = defaultValue) {

    override fun getErrorMessage(): String {
        return errorMessage
    }

    override fun isPatternValid(): Boolean {
        return if (isMandatory) value().trim().length <= minLength else true
    }
}

class DOBField(
    private val errorMessage: String = "",
    private val minLength: Int = 1,
    defaultValue: String = "",
    private val isMandatory: Boolean = true
) : TextFieldState(defaultValue = defaultValue) {

    override fun getErrorMessage(): String {
        return errorMessage
    }

    override fun isPatternValid(): Boolean {
        return if (isMandatory) value().trim().length >= minLength else true
    }
}


class NameField(
    private val errorMessage: String = "",
    private val minLength: Int = 3,
    defaultValue: String = "",
    private val isMandatory: Boolean = true
) : TextFieldState(defaultValue = defaultValue) {

    override fun getErrorMessage(): String {
        return errorMessage
    }

    override fun isPatternValid(): Boolean {
        return if (isMandatory) value().trim().length >= minLength else true
    }
}


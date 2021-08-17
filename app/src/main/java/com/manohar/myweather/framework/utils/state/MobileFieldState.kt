package com.manohar.myweather.framework.ui.utils

import com.manohar.myweather.framework.utils.RegexPattern


class MobileFieldState(value: String) : TextFieldState(value) {

    override fun getErrorMessage(): String {
        return "Please enter valid phone number"
    }

    override fun isPatternValid(): Boolean {
        return value().matches(Regex(RegexPattern.MOBILE_REGEX))
    }
}

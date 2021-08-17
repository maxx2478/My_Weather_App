package com.manohar.myweather.framework.ui.utils

import com.manohar.myweather.framework.utils.RegexPattern


class EmailTextFieldState(value: String?, private val mandatory: Boolean = true) :
    TextFieldState() {
    init {
        text.value = value
    }

    override fun getErrorMessage(): String {
        return "Please enter valid email address"
    }

    override fun isPatternValid(): Boolean {
        return if (mandatory || value().isNotBlank()) value().matches(Regex(RegexPattern.EMAIL_REGEX))
        else true
    }
}
package com.manohar.myweather.framework.utils

object RegexPattern {
    const val AT_LEAST_3_CHARCTER_REGEX = "^[a-zA-Z0-9]{3,}$"
    const val BANK_CHARACTERS_LENGTH = "^\\d{9,18}\$"
    const val EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$"
    const val IFSC_CHARACTERS_LENGTH = 11
    const val LETTER_REGEX = "^[A-Za-z ]*$"
    const val MOBILE_REGEX = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$"
    const val PAN_REGEX = "^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$"
    const val PERCENTAGE_REGEX = "^[0-9]{2,2}[.]{1,1}[0-9]{2,2}$"
    const val REQUIRED_NAME_CHARACTERS_LENGTH = 5
    const val OTP_REGEX = "\\d{6}"
}
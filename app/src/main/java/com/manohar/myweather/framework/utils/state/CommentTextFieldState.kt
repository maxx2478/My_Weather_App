package com.manohar.myweather.framework.ui.utils


class CommentTextFieldState(
    val value: String,
    val minLength: Int = 10,
    val mandatory: Boolean = true
) : TextFieldState(value) {
    override fun getErrorMessage(): String {
        return "This field must have at-least $minLength characters"
    }

    override fun isPatternValid(): Boolean {
        return if (mandatory) value().length >= minLength else true
    }
}
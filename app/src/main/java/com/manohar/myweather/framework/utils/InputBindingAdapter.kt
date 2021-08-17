package com.manohar.myweather.framework.utils

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText

class InputBindingAdapter {
    companion object {

    @JvmStatic
    @BindingAdapter("app:showInputTextProperlyAfterFocusChange")
    fun isFocused(
        inputEditText: EditText,
        showInputTextProperlyAfterFocusChange: Boolean
    ) {
        if (showInputTextProperlyAfterFocusChange) {
            inputEditText.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    inputEditText.setSelection(0)
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:onFocusChange")
    fun isFocused(
        inputEditText: TextInputEditText,
        onFocusChangeListener: OnFocusChangeListener
    ) {
        inputEditText.setOnFocusChangeListener { _, hasFocus ->
            onFocusChangeListener.onFocusChange(hasFocus)
        }
    }

    interface OnFocusChangeListener {
        fun onFocusChange(isFocused: Boolean)
    }
}
}
package com.manohar.myweather.framework.ui.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

abstract class TextFieldState(defaultValue: String = "", notNull: Boolean = false) {
    var text = MutableLiveData<String>().apply {
        value = defaultValue
    }

    private val _wasFocused = MutableLiveData<Boolean>()

    private val _displayError = MediatorLiveData<String>().apply {
        var wasEverFocused = false
        addSource(_wasFocused) {
            if (it) {
                wasEverFocused = true
            }
            value = if (wasEverFocused) if (isPatternValid()) null else getErrorMessage() else null
        }
        addSource(text) {
            value =
                if (wasEverFocused) if (isPatternValid()) null else getErrorMessage() else null
        }
    }

    val valid: LiveData<Boolean> = Transformations.map(text) {
        return@map isPatternValid()
    }

    val displayError: LiveData<String>
        get() = _displayError

    fun setFocused(boolean: Boolean) {
        _wasFocused.value = boolean
    }

    fun value() = text.value ?: ""

    protected abstract fun getErrorMessage(): String

    abstract fun isPatternValid(): Boolean
}


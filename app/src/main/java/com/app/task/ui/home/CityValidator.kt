package com.app.task.ui.home

import android.text.Editable
import android.text.TextWatcher
import java.util.regex.Pattern

class CityValidator() : TextWatcher {
    var isValid = false
        private set

    override fun afterTextChanged(editableText: Editable) {
        isValid = isValidCity(editableText)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { /*No-op*/
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { /*No-op*/
    }

    companion object {
        val CITY_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%\\-]{1,256}"
        )
        fun isValidCity(city: CharSequence?): Boolean {
            return city != null && CITY_PATTERN.matcher(city).matches()
        }
    }
}
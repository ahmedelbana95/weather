package com.app.task.utils

import com.app.task.models.Message

sealed class ViewState<out T> {
    object Loading : ViewState<Nothing>()
    class Success<out T>(val data: T) : ViewState<T>()
    class Error(val message: Message) : ViewState<Nothing>()
}

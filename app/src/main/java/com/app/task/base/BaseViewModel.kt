package com.app.task.base

import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.app.task.network.InternetConnectionManager
import com.app.task.network.InternetConnectionManagerInterface
import com.app.task.utils.managers.ApiRequestManager

abstract class BaseViewModel(resources: Resources, context: Context) : ViewModel() {
    var apiRequestManager: ApiRequestManager = ApiRequestManager(resources)
    var internetConnectionManager: InternetConnectionManagerInterface =
        InternetConnectionManager(context)
}
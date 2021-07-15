package com.app.task.ui.home

import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import com.app.task.R
import com.app.task.base.BaseViewModel
import com.app.task.models.Message
import com.app.task.models.weather.Weather
import com.app.task.repositories.SearchRepoInterface
import com.app.task.utils.ViewState

class HomeViewModel(
    private val searchRepoInterface: SearchRepoInterface,
    private val resources: Resources,
    context: Context,
) : BaseViewModel(resources, context) {

    val searchViewState = MutableLiveData<ViewState<Weather>>()

    fun search(city: String, key: String) {
        if (internetConnectionManager.isConnectedToInternet) {
            searchViewState.value = ViewState.Loading

            apiRequestManager.executeOnMain(
                request = {
                    searchRepoInterface.search(
                        city,
                        key
                    )
                },
                onSuccess = { data, _ ->
                    searchViewState.value = ViewState.Success(data)
                },
                onFailure = {
                    searchViewState.value = ViewState.Error(it)
                }
            )
        } else {
            searchViewState.value =
                ViewState.Error(
                    Message(
                        resources.getString(R.string.no_internet_connection)
                    )
                )
        }
    }
}
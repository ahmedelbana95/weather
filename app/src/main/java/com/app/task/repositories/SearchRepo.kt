package com.app.task.repositories

import com.app.task.models.weather.Weather
import com.app.task.network.NetworkApiClient
import com.app.task.utils.Constants
import retrofit2.Response
import retrofit2.http.*

interface SearchAPIs {
    @GET(Constants.Endpoints.SEARCH)
    suspend fun search(
        @Query("q") city: String,
        @Query("appid") key: String,
    ): Response<Weather>
}

interface SearchRepoInterface {
    suspend fun search(city: String, key: String)
            : Response<Weather>
}

class SearchRepo : SearchRepoInterface {
    private val api = NetworkApiClient.apiClient.create(SearchAPIs::class.java)

    override suspend fun search(
        city: String,
        key: String,
    ) = api.search(city, key)
}
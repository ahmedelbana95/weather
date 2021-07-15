package com.app.task.network

import androidx.multidex.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val baseURL = "https://api.openweathermap.org/"

object NetworkApiClient {
    private var retrofit: Retrofit? = null

    var isLangChanged = false
    var langCode = "en"
    val apiClient: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = makeRetrofit()
            }
            return retrofit!!
        }

    private fun makeRetrofit(
        vararg interceptors: Interceptor,
        baseUrl: String = baseURL
    ): Retrofit {

        val gson = GsonBuilder()
            .create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(makeHttpClient(interceptors))
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    private fun makeHttpClient(interceptors: Array<out Interceptor>): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(headersInterceptor())
            .addInterceptor(loggingInterceptor())
            .apply {
                interceptors().addAll(interceptors)
            }
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .cache(null).build()
    }

    private fun headersInterceptor() = Interceptor { chain ->
        chain.proceed(
            chain.request().newBuilder().build()
        )
    }

    private fun loggingInterceptor() = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }
}
package com.app.task.utils.managers

import android.content.Context
import com.app.task.models.weather.Weather__1
import kotlin.collections.HashSet

interface SharedPreferencesManager {
    fun updateRecentResults(newResults: List<Weather__1>)
    fun getRecentResults(): Set<String>
}

@Suppress("UNCHECKED_CAST")
class SharedPreferencesManagerLmp(context: Context) : SharedPreferencesManager {
    private var sharedPreferencesName = "task"
    private val searchHistoryKey = "searchHistory"
    private var sharedPreferences =
        context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)

    override fun updateRecentResults(newResults: List<Weather__1>) {
        val results = HashSet<String>()
        for (i in newResults){
            results.add(i.main!!)
        }
        sharedPreferences.edit().putStringSet(searchHistoryKey, results).apply()
    }
    override fun getRecentResults(): Set<String> {
        return sharedPreferences.getStringSet(searchHistoryKey, emptySet()) ?: emptySet()
    }
}

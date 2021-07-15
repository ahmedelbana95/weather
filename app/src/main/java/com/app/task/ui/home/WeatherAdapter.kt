package com.app.task.ui.home

import android.content.Context
import com.app.task.R
import com.app.task.base.BaseAdapter
import com.app.task.databinding.ItemWeatherBinding

class WeatherAdapter(
    override var context: Context,
    override var listData: List<String>,
) : BaseAdapter<ItemWeatherBinding, String>(context, listData) {
    override fun layoutID(): Int =
        R.layout.item_weather


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        binding.details = listData[position]
    }
}
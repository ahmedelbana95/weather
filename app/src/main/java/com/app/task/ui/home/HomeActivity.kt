package com.app.task.ui.home

import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.task.R
import com.app.task.base.BaseActivity
import com.app.task.databinding.ActivityHomeBinding
import com.app.task.models.weather.Weather__1
import com.app.task.repositories.SearchRepo
import com.app.task.utils.ViewState
import com.app.task.utils.SBManager
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.search_bar.*

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override val layoutResource: Int
        get() = R.layout.activity_home
    override val isEnableToolbar: Boolean
        get() = true
    override val isEnableBack: Boolean
        get() = false

    private var mCityValidator: CityValidator? = null
    var valid: Boolean? = null

    private lateinit var searchViewModel: HomeViewModel
    override fun initializeComponents() {
        setToolbarTitle(getString(R.string.app_name))
        valid = true
        searchViewModel = HomeViewModel(SearchRepo(), resources, this)
        mCityValidator = CityValidator()
        et_search!!.addTextChangedListener(mCityValidator)
        setupListeners()
        setUpObservers()
    }


    override fun onResume() {
        et_search.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        super.onResume()
    }

    private fun setupListeners() {
        et_search.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH ->
                    if (mCityValidator!!.isValid) {
                        performSearch(et_search.text.toString(),
                            getString(R.string.weather_key))
                    } else {
                        SBManager.displayError(this, getString(R.string.invalid_data))
                    }
            }
            return@setOnEditorActionListener true
        }
        btnRetry.setOnClickListener {
            if (mCityValidator!!.isValid) {
                performSearch(et_search.text.toString(),
                    getString(R.string.weather_key))
            } else {
                SBManager.displayError(this, getString(R.string.invalid_data))
            }
        }
    }

    private fun performSearch(city: String, key: String) {
        searchViewModel.search(city, key)
    }

    private fun setUpObservers() {
        searchViewModel.searchViewState.observe(this, {
            when (it) {
                is ViewState.Loading -> Log.e("LOG", "LOADING")
                is ViewState.Success -> {
                    if (it.data.weather!!.isEmpty()) {
                        if (mSharedPrefManager.getRecentResults().toList().isNotEmpty()) {
                            getViewDataBinding().rvResults.visibility = View.VISIBLE
                            getViewDataBinding().rvResults.layoutManager = LinearLayoutManager(this)
                            getViewDataBinding().rvResults.adapter =
                                WeatherAdapter(this, mSharedPrefManager.getRecentResults().toList())
                            btnRetry.visibility = View.VISIBLE
                        } else {
                            getViewDataBinding().clEmptyPayments.visibility = View.VISIBLE
                        }
                    } else {
                        var list: ArrayList<String>? = null
                        list = ArrayList()
                        for (i in it.data.weather!!) {
                            list.add(i.main!!)
                        }
                        getViewDataBinding().rvResults.visibility = View.VISIBLE
                        getViewDataBinding().rvResults.layoutManager = LinearLayoutManager(this)
                        getViewDataBinding().rvResults.adapter =
                            WeatherAdapter(this, list.toList())
                        for (i in it.data.weather!!) {
                            if (!mCityValidator!!.isValid) {
                                SBManager.displayError(this, getString(R.string.invalid_data))
                                valid = false
                            }
                        }
                        if (valid!!) {
                            updateRecentResults(it.data.weather!!)
                        }
                        btnRetry.visibility = View.GONE
                        getViewDataBinding().clEmptyPayments.visibility = View.GONE
                    }
                }
                is ViewState.Error -> {
                    getViewDataBinding().rvResults.visibility = View.VISIBLE
                    getViewDataBinding().rvResults.layoutManager = LinearLayoutManager(this)
                    getViewDataBinding().rvResults.adapter =
                        WeatherAdapter(this, mSharedPrefManager.getRecentResults().toList())
                    SBManager.displayError(this, it.message.errorMessage)
                    btnRetry.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun updateRecentResults(list: List<Weather__1>) {
        mSharedPrefManager.updateRecentResults(list)
    }
}
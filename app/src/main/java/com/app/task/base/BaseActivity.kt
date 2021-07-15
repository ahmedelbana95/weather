package com.app.task.base
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.app.task.R
import com.app.task.utils.managers.SharedPreferencesManager
import com.app.task.utils.managers.SharedPreferencesManagerLmp

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    private var viewDataBinding: T? = null

    protected lateinit var mSharedPrefManager: SharedPreferencesManager

    internal var toolbar: Toolbar? = null

    protected var mSavedInstanceState: Bundle? = null

    protected var title: TextView? = null

    /**
     * @return the layout resource id
     */
    protected abstract val layoutResource: Int

    /**
     * it is a boolean method which tell my if toolbar
     * is enabled or not
     */
    protected abstract val isEnableToolbar: Boolean

    /**
     * it is a boolean method which tell if back button
     * is enabled or not
     */
    protected abstract val isEnableBack: Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSharedPrefManager = SharedPreferencesManagerLmp(this)

        viewDataBinding = DataBindingUtil.setContentView(this, layoutResource)


        this.mSavedInstanceState = savedInstanceState

        if (isEnableToolbar) {
            configureToolbar()
        }
        initializeComponents()
    }

    fun getViewDataBinding(): T {
        return viewDataBinding!!
    }

    fun setToolbarTitle(titleId: String) {
        if (toolbar != null) {
            title?.text = titleId
        }
    }

    protected abstract fun initializeComponents()

    private fun configureToolbar() {
        toolbar = findViewById<View>(R.id.toolbar_main) as Toolbar?
        title = findViewById(R.id.tvTitle)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        // check if enable back
        if (isEnableBack) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar?.setNavigationIcon(R.drawable.ic_back)
        }
    }
}





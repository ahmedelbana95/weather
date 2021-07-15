package com.app.task.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import com.app.task.R
import com.google.android.material.snackbar.Snackbar

object SBManager {

    private var snack: Snackbar? = null

    private fun initSnack(context: Context): Snackbar {
        snack = Snackbar.make(
            (context as Activity).findViewById(android.R.id.content),
            "",
            Snackbar.LENGTH_LONG
        )
        snack?.setAction("(x)") { snack?.dismiss() }
        snack?.view?.layoutParams =
            (snack?.view?.layoutParams as FrameLayout.LayoutParams).apply { gravity = Gravity.TOP }

        return snack!!
    }

    fun displayError(context: Context, errMsg: String? = null) {
        snack = initSnack(context)
        snack?.setActionTextColor(Color.WHITE)
        snack?.setText(errMsg ?: context.getString(R.string.error))
        snack?.show()
        Log.d("check_snack", "err snake")
    }

}
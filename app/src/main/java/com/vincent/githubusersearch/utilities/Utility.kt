package com.vincent.githubusersearch.utilities

import android.widget.Toast

import androidx.annotation.StringRes

import com.vincent.githubusersearch.AppController
import kotlin.math.ceil

object Utility {

    fun toastShort(msg: String) {
        Toast.makeText(AppController.instance.applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    fun toastShort(@StringRes msgResId: Int) {
        toastShort(AppController.instance.applicationContext.getString(msgResId))
    }

    fun toastLong(msg: String) {
        Toast.makeText(AppController.instance.applicationContext, msg, Toast.LENGTH_LONG).show()
    }

    fun toastLong(@StringRes msgResId: Int) {
        toastLong(AppController.instance.applicationContext.getString(msgResId))
    }

    fun getTotalPageCount(totalCount: Int, countPerPage: Int): Int {
        val totalPage = ceil(totalCount.toDouble() / countPerPage).toInt()
        return if (totalCount > 1000) 1000 else totalPage
    }
}

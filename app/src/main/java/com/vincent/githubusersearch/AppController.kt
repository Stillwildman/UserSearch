package com.vincent.githubusersearch

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

/**
 * <h1>AppController</h1>
 * <p>
 * ���ѦbApp�ͩR�g���̤@�w�|�s�b��Application instance�A<br>
 * �i�H�b����a����o��instance�Ӧs��resources��functions�A�i��֨ϥ�Activity context�C
 * </p>
 * ����App�@�q���వ��function�|��b�o�̡A�Ҧp�}�ҥ~���s���� or ���ón����L��
 */
class AppController : MultiDexApplication() {

    companion object {
        @get:Synchronized
        lateinit var instance: AppController
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun openWebWithBrowser(webUrl: String?) {
        if (webUrl != null && webUrl.startsWith("http")) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.data = Uri.parse(webUrl)

            startActivity(intent)
        }
    }

    fun hideKeyboard(view: View) {
        val imm = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.applicationWindowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

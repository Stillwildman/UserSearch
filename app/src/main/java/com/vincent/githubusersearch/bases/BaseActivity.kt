package com.vincent.githubusersearch.bases

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.vincent.githubusersearch.R

/**
 * <h1>BaseActivity</h1>
 *
 * 1. 給所有的Activity繼承，先把基本會做的事在這裡做好<br>
 * 2. 使用DataBinding
 */
abstract class BaseActivity<BindingView : ViewDataBinding> : AppCompatActivity() {

    protected val tag = javaClass.simpleName

    // 一定要有的layoutId
    protected abstract fun getLayoutId(): Int

    // Toolbar是選擇性的，若不實做可以給null
    protected abstract fun getToolbar(): Toolbar?

    protected abstract fun init()

    protected val mBinding: BindingView by lazy { DataBindingUtil.setContentView(this, getLayoutId()) as BindingView }

    // 這裡刻意讓onCreate設為final，繼承的子類別只需實做abstract methods就好
    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()

        init()
    }

    private fun initToolbar() {
        setSupportActionBar(getToolbar())

        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> onBackPressed()
        }
        return true
    }
}
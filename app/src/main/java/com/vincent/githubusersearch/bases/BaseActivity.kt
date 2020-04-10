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
 * 1. ���Ҧ���Activity�~�ӡA����򥻷|�����Ʀb�o�̰��n<br>
 * 2. �ϥ�DataBinding
 */
abstract class BaseActivity<BindingView : ViewDataBinding> : AppCompatActivity() {

    protected val tag = javaClass.simpleName

    // �@�w�n����layoutId
    protected abstract fun getLayoutId(): Int

    // Toolbar�O��ܩʪ��A�Y���갵�i�H��null
    protected abstract fun getToolbar(): Toolbar?

    protected abstract fun init()

    protected val mBinding: BindingView by lazy { DataBindingUtil.setContentView(this, getLayoutId()) as BindingView }

    // �o�̨�N��onCreate�]��final�A�~�Ӫ��l���O�u�ݹ갵abstract methods�N�n
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
package com.vincent.githubusersearching.bases;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseActivity<BindingView extends ViewDataBinding> extends AppCompatActivity {

    protected final String TAG = getClass().getSimpleName();

    protected abstract int getLayoutId();

    protected abstract Toolbar getToolbar();

    protected abstract void init();

    protected BindingView mBinding;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, getLayoutId());

        initToolbar();

        init();
    }

    private void initToolbar() {
        setSupportActionBar(getToolbar());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
}

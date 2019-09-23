package com.vincent.githubusersearch.ui;

import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vincent.githubusersearch.AppController;
import com.vincent.githubusersearch.R;
import com.vincent.githubusersearch.bases.BaseActivity;
import com.vincent.githubusersearch.callbacks.PagingStatusCallback;
import com.vincent.githubusersearch.databinding.ActivityMainBinding;
import com.vincent.githubusersearch.model.User;
import com.vincent.githubusersearch.ui.adapters.UserListAdapter;
import com.vincent.githubusersearch.utilities.Utility;
import com.vincent.githubusersearch.viewmodel.UserViewModel;

public class UiMainActivity extends BaseActivity<ActivityMainBinding> implements PagingStatusCallback {

    private UserViewModel viewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Toolbar getToolbar() {
        return mBinding.toolbar;
    }

    @Override
    protected void init() {
        initRecycler();
        initViewModel();
    }

    private void initRecycler() {
        mBinding.layoutContent.recycler.setLayoutManager(new LinearLayoutManager(this));
        mBinding.layoutContent.recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        mBinding.layoutContent.recycler.setAdapter(new UserListAdapter(new DiffUtil.ItemCallback<User>() {
            @Override
            public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.getName().equals(newItem.getName());
            }

            @Override
            public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.getName().equals(newItem.getName()) && oldItem.getAvatarUrl().equals(newItem.getAvatarUrl());
            }
        }));
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        viewModel.setStatusCallback(this);

        mBinding.setUserViewModel(viewModel);

        observeStatus();
    }

    private void observeStatus() {
        viewModel.liveLoadingStatus.observe(this, this::setLoading);
        viewModel.liveErrorMessage.observe(this, Utility.INSTANCE::toastLong);
    }

    @Override
    public void getData() {
        AppController.Companion.getInstance().hideKeyboardByGivenView(mBinding.layoutContent.editQuery);

        viewModel.getUserList(getQueryString()).observe(this, userPagedList -> getUserListAdapter().updateList(userPagedList));
    }

    @Override
    public void onLoading(boolean isLoading) {
        viewModel.liveLoadingStatus.postValue(isLoading);
    }

    @Override
    public void onFailed(String errorMessage) {
        Log.e(TAG, errorMessage);
        viewModel.liveErrorMessage.setValue(errorMessage);
    }

    private void setLoading(boolean isLoading) {
        mBinding.setIsLoading(isLoading);
    }

    private String getQueryString() {
        return mBinding.layoutContent.editQuery.getText().toString();
    }

    private UserListAdapter getUserListAdapter() {
        return (UserListAdapter) mBinding.layoutContent.recycler.getAdapter();
    }
}

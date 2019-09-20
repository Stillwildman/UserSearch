package com.vincent.githubusersearching.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vincent.githubusersearching.AppController;
import com.vincent.githubusersearching.R;
import com.vincent.githubusersearching.bases.BaseActivity;
import com.vincent.githubusersearching.callbacks.PagingStatusCallback;
import com.vincent.githubusersearching.databinding.ActivityMainBinding;
import com.vincent.githubusersearching.model.ItemSearchResult;
import com.vincent.githubusersearching.ui.adapters.UserListAdapter;
import com.vincent.githubusersearching.utilities.Utility;
import com.vincent.githubusersearching.viewmodel.UserViewModel;

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
        mBinding.layoutContent.recycler.setAdapter(new UserListAdapter(new DiffUtil.ItemCallback<ItemSearchResult.ItemUser>() {
            @Override
            public boolean areItemsTheSame(@NonNull ItemSearchResult.ItemUser oldItem, @NonNull ItemSearchResult.ItemUser newItem) {
                return oldItem.getName().equals(newItem.getName());
            }

            @Override
            public boolean areContentsTheSame(@NonNull ItemSearchResult.ItemUser oldItem, @NonNull ItemSearchResult.ItemUser newItem) {
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
        viewModel.liveErrorMessage.observe(this, Utility::toastLong);
    }

    @Override
    public void getData() {
        AppController.getInstance().hideKeyboardByGivenView(mBinding.layoutContent.editQuery);

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

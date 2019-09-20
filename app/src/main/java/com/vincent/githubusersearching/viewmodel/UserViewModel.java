package com.vincent.githubusersearching.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.vincent.githubusersearching.callbacks.PagingStatusCallback;
import com.vincent.githubusersearching.model.ItemSearchResult;
import com.vincent.githubusersearching.paging.UserDataRepo;

public class UserViewModel extends ViewModel {

    private PagingStatusCallback statusCallback;

    public final MutableLiveData<Boolean> liveLoadingStatus = new MutableLiveData<>();
    public final MutableLiveData<String> liveErrorMessage = new MutableLiveData<>();

    public void setStatusCallback(PagingStatusCallback statusCallback) {
        this.statusCallback = statusCallback;
    }

    public LiveData<PagedList<ItemSearchResult.ItemUser>> getUserList(String keyword) {
        return new UserDataRepo(keyword, statusCallback).getUserList();
    }

    public void getData() {
        if (!isLoading()) {
            statusCallback.getData();
        }
    }

    private boolean isLoading() {
        return liveLoadingStatus.getValue() != null && liveLoadingStatus.getValue();
    }
}

package com.vincent.githubusersearch.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.vincent.githubusersearch.callbacks.PagingStatusCallback;
import com.vincent.githubusersearch.model.User;
import com.vincent.githubusersearch.paging.GitHubUserDataRepo;

public class UserViewModel extends ViewModel {

    private PagingStatusCallback statusCallback;

    public final MutableLiveData<Boolean> liveLoadingStatus = new MutableLiveData<>();
    public final MutableLiveData<String> liveErrorMessage = new MutableLiveData<>();

    public void setStatusCallback(PagingStatusCallback statusCallback) {
        this.statusCallback = statusCallback;
    }

    public LiveData<PagedList<User>> getUserList(String keyword) {
        return new GitHubUserDataRepo(keyword, statusCallback).getUserList();
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

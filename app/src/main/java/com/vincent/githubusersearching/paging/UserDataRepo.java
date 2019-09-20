package com.vincent.githubusersearching.paging;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.vincent.githubusersearching.callbacks.PagingStatusCallback;
import com.vincent.githubusersearching.model.ItemSearchResult;
import com.vincent.githubusersearching.model.PageConfigParams;

public class UserDataRepo implements PageConfigParams {

    private final String keyword;
    private final PagingStatusCallback statusCallback;

    public UserDataRepo(String keyword, PagingStatusCallback statusCallback) {
        this.keyword = keyword;
        this.statusCallback = statusCallback;
    }

    public LiveData<PagedList<ItemSearchResult.ItemUser>> getUserList() {
        return new LivePagedListBuilder<>(new UserDataSourceFactory(keyword, statusCallback), CONFIG)
                .setInitialLoadKey(INITIAL_LOAD_KEY)
                .build();
    }
}

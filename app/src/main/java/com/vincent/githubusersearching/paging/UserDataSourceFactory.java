package com.vincent.githubusersearching.paging;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.vincent.githubusersearching.callbacks.PagingStatusCallback;
import com.vincent.githubusersearching.model.ItemSearchResult;

public class UserDataSourceFactory extends DataSource.Factory<Integer, ItemSearchResult.ItemUser> {

    private final String keyword;
    private final PagingStatusCallback statusCallback;

    UserDataSourceFactory(String keyword, PagingStatusCallback statusCallback) {
        this.keyword = keyword;
        this.statusCallback = statusCallback;
    }

    @NonNull
    @Override
    public DataSource<Integer, ItemSearchResult.ItemUser> create() {
        return new UserDataSource(keyword, statusCallback);
    }
}

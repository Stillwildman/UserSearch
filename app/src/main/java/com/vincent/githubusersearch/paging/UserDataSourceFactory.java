package com.vincent.githubusersearch.paging;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.vincent.githubusersearch.callbacks.PagingStatusCallback;
import com.vincent.githubusersearch.model.User;

public class UserDataSourceFactory extends DataSource.Factory<Integer, User> {

    private final String keyword;
    private final PagingStatusCallback statusCallback;

    UserDataSourceFactory(String keyword, PagingStatusCallback statusCallback) {
        this.keyword = keyword;
        this.statusCallback = statusCallback;
    }

    @NonNull
    @Override
    public DataSource<Integer, User> create() {
        return new GitHubUserDataSource(keyword, statusCallback);
    }
}

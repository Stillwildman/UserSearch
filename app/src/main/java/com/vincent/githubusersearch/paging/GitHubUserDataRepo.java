package com.vincent.githubusersearch.paging;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.vincent.githubusersearch.bases.BasePageConfig;
import com.vincent.githubusersearch.callbacks.PagingStatusCallback;
import com.vincent.githubusersearch.model.User;

public class GitHubUserDataRepo extends BasePageConfig {

    private final String keyword;
    private final PagingStatusCallback statusCallback;

    public GitHubUserDataRepo(String keyword, PagingStatusCallback statusCallback) {
        this.keyword = keyword;
        this.statusCallback = statusCallback;
    }

    public LiveData<PagedList<User>> getUserList() {
        return new LivePagedListBuilder<>(new UserDataSourceFactory(keyword, statusCallback), getConfig())
                .setInitialLoadKey(getInitialPageKey())
                .build();
    }

    @Override
    protected int getPageSize() {
        return 10;
    }

    @Override
    protected int getInitialPageKey() {
        return 1;
    }
}

package com.vincent.githubusersearch.bases;

import androidx.paging.PagedList;

public abstract class BasePageConfig {

    protected abstract int getPageSize();

    protected abstract int getInitialPageKey();

    protected PagedList.Config getConfig() {
        return new PagedList.Config.Builder()
                .setPageSize(getPageSize())
                .setInitialLoadSizeHint(getPageSize())
                .setEnablePlaceholders(false)
                .build();
    }
}

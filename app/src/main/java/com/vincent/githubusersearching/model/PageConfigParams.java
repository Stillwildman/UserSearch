package com.vincent.githubusersearching.model;

import androidx.paging.PagedList;

public interface PageConfigParams {

    int PAGE_SIZE = 10;

    int INITIAL_LOAD_KEY = 1;

    PagedList.Config CONFIG = new PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build();

}

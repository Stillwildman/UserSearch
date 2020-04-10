package com.vincent.githubusersearch.bases

import androidx.paging.PagedList

/**
 * For Paging config.
 */
abstract class BasePageConfig {

    protected abstract fun getPageSize(): Int

    protected abstract fun getInitialPageKey(): Int

    protected val config: PagedList.Config
        get() = PagedList.Config.Builder()
                .setPageSize(getPageSize())
                .setInitialLoadSizeHint(getPageSize())
                .setEnablePlaceholders(false)
                .build()
}
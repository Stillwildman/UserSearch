package com.vincent.githubusersearch.paging

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.vincent.githubusersearch.bases.BasePageConfig
import com.vincent.githubusersearch.callbacks.PagingStatusCallback
import com.vincent.githubusersearch.model.User

/**
 * <p>在這裡對PagedList的LiveData做設定，主要是產生 [PagedList.Config] 並設置PageSize & InitialPageKey。</p>
 *
 * 建立LiveData時會給予一個新的 [UserDataSourceFactory]，接著會在UserDataSourceFactory中create [UserDataSource]。
 */
class UserDataRepo(private val keyword: String, private val statusCallback: PagingStatusCallback?) : BasePageConfig() {

    val userList: LiveData<PagedList<User>>
        get() = LivePagedListBuilder(UserDataSourceFactory(keyword, statusCallback), config)
                .setInitialLoadKey(getInitialPageKey())
                .build()

    override fun getPageSize(): Int {
        return 10
    }

    override fun getInitialPageKey(): Int {
        return 1
    }

}
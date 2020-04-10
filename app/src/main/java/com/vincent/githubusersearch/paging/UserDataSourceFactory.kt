package com.vincent.githubusersearch.paging

import androidx.paging.DataSource
import com.vincent.githubusersearch.callbacks.PagingStatusCallback
import com.vincent.githubusersearch.model.User

/**
 * DataSourceFactory�A�b�o�̲��� [UserDataSource]
 */
class UserDataSourceFactory internal constructor(
        private val keyword: String,
        private val statusCallback: PagingStatusCallback?) : DataSource.Factory<Int, User>() {

    override fun create(): DataSource<Int, User> {
        return UserDataSource(keyword, statusCallback)
    }

}
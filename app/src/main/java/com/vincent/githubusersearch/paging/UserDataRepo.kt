package com.vincent.githubusersearch.paging

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.vincent.githubusersearch.bases.BasePageConfig
import com.vincent.githubusersearch.callbacks.PagingStatusCallback
import com.vincent.githubusersearch.model.User

/**
 * <p>�b�o�̹�PagedList��LiveData���]�w�A�D�n�O���� [PagedList.Config] �ó]�mPageSize & InitialPageKey�C</p>
 *
 * �إ�LiveData�ɷ|�����@�ӷs�� [UserDataSourceFactory]�A���۷|�bUserDataSourceFactory��create [UserDataSource]�C
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
package com.vincent.githubusersearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.vincent.githubusersearch.callbacks.PagingStatusCallback
import com.vincent.githubusersearch.model.User
import com.vincent.githubusersearch.paging.UserDataRepo

class UserViewModel : ViewModel() {

    private var statusCallback: PagingStatusCallback? = null

    val liveLoadingStatus = MutableLiveData<Boolean>()
    val liveErrorMessage = MutableLiveData<String>()

    fun setStatusCallback(statusCallback: PagingStatusCallback) {
        this.statusCallback = statusCallback
    }

    fun getUserList(keyword: String): LiveData<PagedList<User>> {
        return UserDataRepo(keyword, statusCallback).userList
    }

    fun getData() {
        if (!isLoading()) {
            statusCallback?.requireData()
        }
    }

    private fun isLoading(): Boolean {
        return liveLoadingStatus.value ?: false
    }
}
package com.vincent.githubusersearch.callbacks

interface PagingStatusCallback {

    fun requireData()

    fun onLoading(isLoading: Boolean)

    fun onFailed(errorMessage: String?)

}
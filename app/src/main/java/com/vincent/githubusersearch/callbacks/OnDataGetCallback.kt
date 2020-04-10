package com.vincent.githubusersearch.callbacks

interface OnDataGetCallback<Item> {

    fun onDataGet(item: Item?)

    fun onDataGetFailed(errorMessage: String?)

}
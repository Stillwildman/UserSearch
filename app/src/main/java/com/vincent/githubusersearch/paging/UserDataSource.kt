package com.vincent.githubusersearch.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.vincent.githubusersearch.callbacks.OnDataGetCallback
import com.vincent.githubusersearch.callbacks.PagingStatusCallback
import com.vincent.githubusersearch.model.ItemSearchResult
import com.vincent.githubusersearch.model.User
import com.vincent.githubusersearch.network.DataCallbacks.getGitHubUsers
import com.vincent.githubusersearch.utilities.Utility.getTotalPageCount
import java.util.*

/**
 * <h1>UserDataSource</h1>
 *
 * <p>
 * 在這裡進行網路活動，並且把資料取得狀態Callback回去。<br>
 * 採用PageKeyedDataSource，會判斷是否還有下一頁的資料，<br>
 * 有的話給予下一筆的API page參數，接著自動取得新的資料。
 * </p>
 */
class UserDataSource(private val keyword: String, private val statusCallback: PagingStatusCallback?) : PageKeyedDataSource<Int, User>() {

    private val tag = "GitHubUserDataSource"

    /** 第一次載入時，每一頁的筆數由 [UserDataRepo] 設定，初始page固定是1. */
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, User>) {
        statusCallback?.onLoading(true)

        getGitHubUsers(keyword, params.requestedLoadSize, 1 /** Initial page key*/, object : OnDataGetCallback<ItemSearchResult> {
            override fun onDataGet(item: ItemSearchResult?) {
                statusCallback?.onLoading(false)

                item?.let {
                    loadNextPageFromInitial(it, params.requestedLoadSize, callback)
                }
            }

            override fun onDataGetFailed(errorMessage: String?) {
                onFailed(errorMessage)
            }
        })
    }

    private fun loadNextPageFromInitial(item: ItemSearchResult, requestedLoadSize: Int, callback: LoadInitialCallback<Int, User>) {
        val totalPageCount = getTotalPageCount(item.totalCount, requestedLoadSize)
        Log.i(tag, "TotalPageCount: $totalPageCount")

        // 下一頁的API page key，null表示沒有下一頁，就不會再繼續觸發loadAfter了
        val nextPageKey = if (totalPageCount > 1) 2 else null

        callback.onResult(ArrayList<User>(item.userList), null, nextPageKey)
    }

    // 載入下一頁，同上，如果沒有下一頁，page key就給null
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        statusCallback?.onLoading(true)

        getGitHubUsers(keyword, params.requestedLoadSize, params.key, object : OnDataGetCallback<ItemSearchResult> {
            override fun onDataGet(item: ItemSearchResult?) {
                statusCallback!!.onLoading(false)

                item?.let {
                    loadNextPage(it, it.totalCount, params.key, params.requestedLoadSize, callback)
                }
            }

            override fun onDataGetFailed(errorMessage: String?) {
                onFailed(errorMessage)
            }
        })
    }

    private fun loadNextPage(item: ItemSearchResult, totalPage: Int, currentPageKey: Int, requestedLoadSize: Int, callback: LoadCallback<Int, User>) {
        val totalPageCount = getTotalPageCount(totalPage, requestedLoadSize)
        val nextPageKey = if (totalPageCount > currentPageKey) currentPageKey + 1 else null

        callback.onResult(ArrayList<User>(item.userList), nextPageKey)
    }

    // 載入上一頁，原理同上
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        statusCallback?.onLoading(true)

        getGitHubUsers(keyword, params.requestedLoadSize, params.key, object : OnDataGetCallback<ItemSearchResult> {
            override fun onDataGet(item: ItemSearchResult?) {
                statusCallback?.onLoading(false)

                item?.let {
                    loadPreviousPage(it, params.key, callback)
                }
            }

            override fun onDataGetFailed(errorMessage: String?) {
                onFailed(errorMessage)
            }
        })
    }

    private fun loadPreviousPage(item: ItemSearchResult, currentPageKey: Int, callback: LoadCallback<Int, User>) {
        val previousPageKey = if (currentPageKey <= 1) null else currentPageKey - 1
        callback.onResult(ArrayList<User>(item.userList), previousPageKey)
    }

    private fun onFailed(errorMessage: String?) {
        statusCallback?.onLoading(false)
        statusCallback?.onFailed(errorMessage)
    }
}
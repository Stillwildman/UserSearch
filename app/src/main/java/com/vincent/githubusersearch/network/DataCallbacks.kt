package com.vincent.githubusersearch.network

import android.util.Log
import com.vincent.githubusersearch.AppController
import com.vincent.githubusersearch.R
import com.vincent.githubusersearch.callbacks.OnDataGetCallback
import com.vincent.githubusersearch.model.ApiUrl
import com.vincent.githubusersearch.model.ItemSearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.internal.EverythingIsNonNull

@Suppress("SameParameterValue")
object DataCallbacks {

    private const val TAG = "DataCallbacks"

    private fun getApiInterface(): ApiInterface {
        return getApiInterface(ApiUrl.API_BASE_GITHUB_SEARCH)
    }

    private fun getApiInterface(baseUrl: String): ApiInterface {
        return RetrofitAgent.getRetrofit(baseUrl).create(ApiInterface::class.java)
    }

    private fun <Item> enqueue(call: Call<Item>, callback: OnDataGetCallback<Item>) {
        Log.i(TAG, "Call URL: " + call.request().url().toString())

        call.enqueue(object : Callback<Item> {
            @EverythingIsNonNull
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                Log.d(TAG, "Call onResponse!!!\nMessage: ${response.message()} IsSuccessful: ${response.isSuccessful}")

                if (response.isSuccessful) {
                    callback.onDataGet(response.body())
                }
                else {
                    callback.onDataGetFailed(response.message())
                }
            }

            @EverythingIsNonNull
            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.e(TAG, "Call onFailure!!!\n${t.message}")
                callback.onDataGetFailed(t.message)
            }
        })
    }

    fun getGitHubUsers(keyword: String?, perPage: Int, page: Int, callback: OnDataGetCallback<ItemSearchResult>) {
        val clientId = AppController.instance.getString(R.string.github_client_id)
        val clientSecret = AppController.instance.getString(R.string.github_client_secret)

        val call = getApiInterface().getGitHubUsers(keyword, perPage, page, clientId, clientSecret)

        enqueue(call, callback)
    }
}
package com.vincent.githubusersearch.network

import com.vincent.githubusersearch.model.ApiUrl
import com.vincent.githubusersearch.model.ItemSearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(ApiUrl.API_GITHUB_USER)
    fun getGitHubUsers(@Query("q") keyword: String?, @Query("per_page") perPage: Int, @Query("page") page: Int,
                       @Query("client_id") clientId: String, @Query("client_secret") clientSecret: String): Call<ItemSearchResult>
}
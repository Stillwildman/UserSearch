package com.vincent.githubusersearching.network;

import com.vincent.githubusersearching.model.ApiUrl;
import com.vincent.githubusersearching.model.ItemSearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET(ApiUrl.API_GITHUB_USER)
    Call<ItemSearchResult> getGitHubUsers(@Query("q") String keyword, @Query("per_page") int perPage, @Query("page") int page,
                                          @Query("client_id") String clientId, @Query("client_secret") String clientSecret);

}

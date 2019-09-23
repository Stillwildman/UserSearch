package com.vincent.githubusersearch.network;

import android.util.Log;

import com.vincent.githubusersearch.AppController;
import com.vincent.githubusersearch.R;
import com.vincent.githubusersearch.callbacks.OnDataGetCallback;
import com.vincent.githubusersearch.model.ApiUrl;
import com.vincent.githubusersearch.model.ItemSearchResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class DataCallbacks implements ApiUrl {

    private static final String TAG = "DataCallbacks";

    private static ApiInterface getApiInterface(String baseUrl) {
        return WebAgent.getRetrofit(baseUrl).create(ApiInterface.class);
    }

    private static<Item> void enqueue(Call<Item> call, final OnDataGetCallback<Item> callback) {
        Log.i(TAG, "Call URL: " + call.request().url().toString());

        call.enqueue(new Callback<Item>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Log.d(TAG, "Call onResponse!!! "
                        + "\nMessage: " + response.message()
                        + " IsSuccessful: " + response.isSuccessful());

                if (response.isSuccessful()) {
                    callback.onDataGet(response.body());
                }
                else {
                    callback.onDataGetFailed(response.message());
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Log.e(TAG, "Call onFailure!!!\n" + t.getMessage());
                callback.onDataGetFailed(t.getMessage());
            }
        });
    }

    public static void getGitHubUsers(String keyword, int perPage, int page, OnDataGetCallback<ItemSearchResult> callback) {
        String clientId = AppController.Companion.getInstance().getString(R.string.github_client_id);
        String clientSecret = AppController.Companion.getInstance().getString(R.string.github_client_secret);

        Call<ItemSearchResult> call = getApiInterface(API_BASE_GITHUB_SEARCH).getGitHubUsers(keyword, perPage, page, clientId, clientSecret);

        enqueue(call, callback);
    }
}

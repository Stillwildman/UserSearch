package com.vincent.githubusersearch.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.vincent.githubusersearch.callbacks.OnDataGetCallback;
import com.vincent.githubusersearch.callbacks.PagingStatusCallback;
import com.vincent.githubusersearch.model.ItemSearchResult;
import com.vincent.githubusersearch.model.User;
import com.vincent.githubusersearch.network.DataCallbacks;
import com.vincent.githubusersearch.utilities.Utility;

import java.util.ArrayList;

public class GitHubUserDataSource extends PageKeyedDataSource<Integer, User> {

    private static final String TAG = "GitHubUserDataSource";

    private final String keyword;
    private final PagingStatusCallback statusCallback;

    GitHubUserDataSource(String keyword, PagingStatusCallback statusCallback) {
        this.keyword = keyword;
        this.statusCallback = statusCallback;
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, User> callback) {
        statusCallback.onLoading(true);

        final int initialPage = 1;

        DataCallbacks.getGitHubUsers(keyword, params.requestedLoadSize, initialPage, new OnDataGetCallback<ItemSearchResult>() {
            @Override
            public void onDataGet(ItemSearchResult searchResultItem) {
                statusCallback.onLoading(false);

                int totalPageCount = Utility.INSTANCE.getTotalPageCount(searchResultItem.getTotalCount(), params.requestedLoadSize);
                Log.i(TAG, "TotalPageCount: " + totalPageCount);

                Integer nextPageKey = totalPageCount > initialPage ? initialPage + 1 : null;

                callback.onResult(new ArrayList<>(searchResultItem.getUserList()), null, nextPageKey);
            }

            @Override
            public void onDataGetFailed(String errorMessage) {
                statusCallback.onLoading(false);
                statusCallback.onFailed(errorMessage);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, User> callback) {
        statusCallback.onLoading(true);

        DataCallbacks.getGitHubUsers(keyword, params.requestedLoadSize, params.key, new OnDataGetCallback<ItemSearchResult>() {
            @Override
            public void onDataGet(ItemSearchResult searchResultItem) {
                statusCallback.onLoading(false);

                Integer previousPageKey = params.key <= 1 ? null : params.key - 1;

                callback.onResult(new ArrayList<>(searchResultItem.getUserList()), previousPageKey);
            }

            @Override
            public void onDataGetFailed(String errorMessage) {
                statusCallback.onLoading(false);
                statusCallback.onFailed(errorMessage);
            }
        });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, User> callback) {
        statusCallback.onLoading(true);

        DataCallbacks.getGitHubUsers(keyword, params.requestedLoadSize, params.key, new OnDataGetCallback<ItemSearchResult>() {
            @Override
            public void onDataGet(ItemSearchResult searchResultItem) {
                statusCallback.onLoading(false);

                int totalPageCount = Utility.INSTANCE.getTotalPageCount(searchResultItem.getTotalCount(), params.requestedLoadSize);
                Integer nextPageKey = totalPageCount > params.key ? params.key + 1 : null;

                callback.onResult(new ArrayList<>(searchResultItem.getUserList()), nextPageKey);
            }

            @Override
            public void onDataGetFailed(String errorMessage) {
                statusCallback.onLoading(false);
                statusCallback.onFailed(errorMessage);
            }
        });
    }
}

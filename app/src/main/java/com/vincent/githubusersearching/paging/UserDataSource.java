package com.vincent.githubusersearching.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.vincent.githubusersearching.callbacks.OnDataGetCallback;
import com.vincent.githubusersearching.callbacks.PagingStatusCallback;
import com.vincent.githubusersearching.model.ItemSearchResult;
import com.vincent.githubusersearching.network.DataCallbacks;
import com.vincent.githubusersearching.utilities.Utility;

public class UserDataSource extends PageKeyedDataSource<Integer, ItemSearchResult.ItemUser> {

    private static final String TAG = "UserDataSource";

    private final String keyword;
    private final PagingStatusCallback statusCallback;

    UserDataSource(String keyword, PagingStatusCallback statusCallback) {
        this.keyword = keyword;
        this.statusCallback = statusCallback;
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, ItemSearchResult.ItemUser> callback) {
        statusCallback.onLoading(true);

        final int initialPage = 1;

        DataCallbacks.getGitHubUsers(keyword, params.requestedLoadSize, initialPage, new OnDataGetCallback<ItemSearchResult>() {
            @Override
            public void onDataGet(ItemSearchResult searchResultItem) {
                statusCallback.onLoading(false);

                int totalPageCount = Utility.getTotalPageCount(searchResultItem.getTotalCount(), params.requestedLoadSize);
                Log.i(TAG, "TotalPageCount: " + totalPageCount);

                Integer nextPageKey = totalPageCount > initialPage ? initialPage + 1 : null;

                callback.onResult(searchResultItem.getUserList(), initialPage, totalPageCount, null, nextPageKey);
            }

            @Override
            public void onDataGetFailed(String errorMessage) {
                Log.e(TAG, errorMessage);
                statusCallback.onLoading(false);
                statusCallback.onFailed(errorMessage);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, ItemSearchResult.ItemUser> callback) {
        statusCallback.onLoading(true);

        DataCallbacks.getGitHubUsers(keyword, params.requestedLoadSize, params.key, new OnDataGetCallback<ItemSearchResult>() {
            @Override
            public void onDataGet(ItemSearchResult searchResultItem) {
                statusCallback.onLoading(false);

                Integer previousPageKey = params.key <= 1 ? null : params.key - 1;

                callback.onResult(searchResultItem.getUserList(), previousPageKey);
            }

            @Override
            public void onDataGetFailed(String errorMessage) {
                Log.e(TAG, errorMessage);
                statusCallback.onLoading(false);
                statusCallback.onFailed(errorMessage);
            }
        });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, ItemSearchResult.ItemUser> callback) {
        statusCallback.onLoading(true);

        DataCallbacks.getGitHubUsers(keyword, params.requestedLoadSize, params.key, new OnDataGetCallback<ItemSearchResult>() {
            @Override
            public void onDataGet(ItemSearchResult searchResultItem) {
                statusCallback.onLoading(false);

                int totalPageCount = Utility.getTotalPageCount(searchResultItem.getTotalCount(), params.requestedLoadSize);
                Integer nextPageKey = totalPageCount > params.key ? params.key + 1 : null;

                callback.onResult(searchResultItem.getUserList(), nextPageKey);
            }

            @Override
            public void onDataGetFailed(String errorMessage) {
                Log.e(TAG, errorMessage);
                statusCallback.onLoading(false);
                statusCallback.onFailed(errorMessage);
            }
        });
    }
}

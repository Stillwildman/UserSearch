package com.vincent.githubusersearching.callbacks;

public interface PagingStatusCallback {

    void getData();

    void onLoading(boolean isLoading);

    void onFailed(String errorMessage);
}

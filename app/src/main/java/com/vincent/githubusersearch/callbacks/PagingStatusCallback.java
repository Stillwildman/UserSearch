package com.vincent.githubusersearch.callbacks;

public interface PagingStatusCallback {

    void getData();

    void onLoading(boolean isLoading);

    void onFailed(String errorMessage);
}

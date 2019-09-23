package com.vincent.githubusersearch.callbacks;

public interface OnDataGetCallback<Item> {

    void onDataGet(Item item);

    void onDataGetFailed(String errorMessage);
}

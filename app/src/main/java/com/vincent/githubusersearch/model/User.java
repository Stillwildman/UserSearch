package com.vincent.githubusersearch.model;

public interface User {

    String getId();

    String getName();

    String getAvatarUrl();

    String getWebUrl();

    int getNumber();

    void setNumber(int number);

    void onPerformAction();
}

package com.vincent.githubusersearching.utilities;

import android.widget.Toast;

import androidx.annotation.StringRes;

import com.vincent.githubusersearching.AppController;

public class Utility {

    public static void toastShort(String msg) {
        Toast.makeText(AppController.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastShort(@StringRes int msgResId) {
        toastShort(AppController.getInstance().getApplicationContext().getString(msgResId));
    }

    public static void toastLong(String msg) {
        Toast.makeText(AppController.getInstance().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public static void toastLong(@StringRes int msgResId) {
        toastLong(AppController.getInstance().getApplicationContext().getString(msgResId));
    }

    public static int getTotalPageCount(int totalCount, int countPerPage) {
        int totalPage = (int) Math.ceil((double) totalCount / countPerPage);
        return totalCount > 1000 ? 1000 : totalPage;
    }
}

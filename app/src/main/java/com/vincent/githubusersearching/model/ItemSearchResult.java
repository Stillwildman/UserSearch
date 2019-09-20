package com.vincent.githubusersearching.model;

import com.vincent.githubusersearching.AppController;

import java.util.List;

public class ItemSearchResult {

    private int total_count;
    private List<ItemUser> items;

    public int getTotalCount() {
        return total_count;
    }

    public List<ItemUser> getUserList() {
        return items;
    }

    public static class ItemUser {

        private String login;
        private String avatar_url;
        private String html_url;

        private int position;

        public String getName() {
            return login;
        }

        public String getAvatarUrl() {
            return avatar_url;
        }

        public void openWebPage() {
            AppController.getInstance().openWebWithBrowser(html_url);
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position + 1;
        }
    }
}

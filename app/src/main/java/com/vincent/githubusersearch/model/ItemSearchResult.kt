package com.vincent.githubusersearch.model

import com.google.gson.annotations.SerializedName
import com.vincent.githubusersearch.AppController

class ItemSearchResult(@SerializedName("total_count") var totalCount: Int, @SerializedName("items") var userList: List<GitHubUser>) {

    class GitHubUser(var id: Long, var login: String, var avatar_url: String, var html_url: String) : User {

        private var position: Int = 0

        override fun getId(): String {
            return id.toString()
        }

        override fun getName(): String? {
            return login
        }

        override fun getAvatarUrl(): String? {
            return avatar_url
        }

        override fun getWebUrl(): String? {
            return html_url
        }

        override fun getNumber(): Int {
            return position
        }

        override fun setNumber(number: Int) {
            this.position = number + 1
        }

        override fun onPerformAction() {
            AppController.instance.openWebWithBrowser(html_url)
        }
    }
}

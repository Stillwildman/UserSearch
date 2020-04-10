package com.vincent.githubusersearch.model

import com.google.gson.annotations.SerializedName
import com.vincent.githubusersearch.AppController

class ItemSearchResult(@SerializedName("total_count") var totalCount: Int, @SerializedName("items") var userList: List<GitHubUser>) {

    inner class GitHubUser(
            private var id: Long,
            private var login: String,
            private var avatar_url: String,
            private var html_url: String) : User {

        private var position: Int = 0

        override fun getId(): Long {
            return id
        }

        override fun getName(): String {
            return login
        }

        override fun getAvatarUrl(): String {
            return avatar_url
        }

        override fun getWebUrl(): String {
            return html_url
        }

        override var indexNumber: Int
            get() = position
            set(value) {
                this.position = value + 1
            }

        override fun onPerformAction() {
            AppController.instance.openWebWithBrowser(html_url)
        }
    }
}

package com.vincent.githubusersearch.model

interface User {

    fun getId(): Long

    fun getName(): String

    fun getAvatarUrl(): String

    fun getWebUrl(): String

    var indexNumber: Int

    fun onPerformAction()
}